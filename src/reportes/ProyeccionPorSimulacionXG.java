package reportes;
import clubes.Club;
import enums.EstadoPartido;
import eventos.Evento;
import eventos.Remate;
import partidos.Partido;
import torneos.Torneo;
import java.util.List;
import java.util.Random;

public class ProyeccionPorSimulacionXG implements ProyeccionClasificacion {
    private double puntajeCorteHistorico;
    private Random random;

    public ProyeccionPorSimulacionXG(double puntajeCorteHistorico) {
        if (puntajeCorteHistorico <= 0) {
            throw new IllegalArgumentException("El puntaje de corte debe ser postivo");
        }
        this.puntajeCorteHistorico = puntajeCorteHistorico;
        this.random = new Random();
    }

    private double calcularXGPromedio(Torneo torneo, Club club) {
        double xgTottal = 0;
        int partidosJugados = 0;
        for (Partido partido : torneo.getPartidos()) {
            if (partido.getEstado() != EstadoPartido.FINALIZADO) {
            continue;
            }
            if (!partido.getLocal().equals(club) && !partido.getVisitante().equals(club)) {
                continue;
            }
            partidosJugados ++;
            for (Evento evento : partido.getEventos()) {
                if (evento instanceof Remate) {
                    Remate remate = (Remate) evento;
                    if (club.equals(partido.obtenerClubJugador(remate.getJugador()))) {
                        xgTottal += remate.calcularXG();
                    }
                }

            }
        }
        if (partidosJugados == 0) {
            return 1.0;
        }
        return xgTottal / partidosJugados;
    }

    private int simularGoles(double xg) {
        int goles = (int) xg;
        for (int i = 0;  i<5; i++) {
            if (random.nextDouble() < xg/5.0) {
                goles++;
            }
        }
        return goles;
    }
    @Override
    public double calcularProbabilidad(Torneo torneo, Club club) {
        if (torneo == null || club == null) {
            return 0;
        }
        GestorReportes gestor = new GestorReportes();
        List<PosicionTorneo> tabla = gestor.generarTablaPosiciones(torneo);
        int puntosActuales = -1;

        for (PosicionTorneo posicion : tabla) {
            if (posicion.getClub().equals(club)) {
                puntosActuales = posicion.getPuntos();
                break;
            }
        }
        if (puntosActuales == -1) {
            return 0;
        }
        double xgClub = calcularXGPromedio(torneo, club);
        int simulacionesExitosas = 0;
        int cantidadSimulaciones = 100;
        for (int i = 0; i < cantidadSimulaciones; i++) {
            int puntosSimulados = puntosActuales;
            for (Partido partido : torneo.getPartidos()) {
                if (partido.getEstado() == EstadoPartido.FINALIZADO) {
                    continue;
                }
                boolean esLocal = partido.getLocal().equals(club);
                boolean esVisitante = partido.getVisitante().equals(club);
                if (!esLocal && !esVisitante) {
                    continue;
                }
                Club rival = esLocal
                        ? partido.getVisitante()
                        : partido.getLocal();

                double xgRival = calcularXGPromedio(torneo, rival);
                int golesClub = simularGoles(xgClub);
                int golesRival = simularGoles(xgRival);
                if (golesClub > golesRival) {
                    puntosSimulados += 3;

                } else if (golesClub == golesRival) {
                    puntosSimulados++;
                }
            }
            if (puntosSimulados >= puntajeCorteHistorico) {
                simulacionesExitosas++;
            }
        }
        return (double) simulacionesExitosas / cantidadSimulaciones;
    }
}
