package reportes;

import clubes.Club;
import torneos.Torneo;

import java.util.List;

public class ProyeccionPorPromedio implements ProyeccionClasificacion {
    private double puntajeCorteHistorico;
    public ProyeccionPorPromedio (double puntajeCorteHistorico) {
        if (puntajeCorteHistorico <= 0) {
            throw new IllegalArgumentException("El puntaje de corte debe ser positivo");
        }
        this.puntajeCorteHistorico = puntajeCorteHistorico;
    }

    @Override
    public double calcularProbabilidad(Torneo torneo, Club club) {
        if (torneo == null || club == null) {
            return 0;
        }

        GestorReportes gestor = new GestorReportes();
        List<PosicionTorneo> tabla = gestor.generarTablaPosiciones(torneo);
        for (PosicionTorneo posicion : tabla) {
            if (posicion.getClub().equals(club)) {
                if (posicion.getPartidosJugados() == 0) {
                    return 0.5;
                }

                double promedio = (double) posicion.getPuntos() / posicion.getPartidosJugados();
                int cantidadClubes = torneo.getInscriptos().size();
                int partidosTotales = 2 * (cantidadClubes - 1);
                double puntosProyectados = promedio * partidosTotales;
                double probabilidad = 0.5 + (puntosProyectados - puntajeCorteHistorico) / (2 * puntajeCorteHistorico);
                if (probabilidad < 0) {
                    return 0;
                }
                if (probabilidad > 1) {
                    return 1;
                }
                return probabilidad;
            }
        }
        return 0;
    }
}
