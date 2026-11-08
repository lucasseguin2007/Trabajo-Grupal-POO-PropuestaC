package reportes;

import clubes.Club;
import enums.EstadoPartido;
import enums.TipoGol;
import eventos.Gol;
import partidos.Partido;
import personas.Jugador;
import torneos.InscripcionTorneo;
import torneos.Torneo;

import java.util.*;

import java.util.List;

public class GestorReportes {
    public List<PosicionTorneo> generarTablaPosiciones(Torneo torneo) {
        List<PosicionTorneo> tabla = new ArrayList<>();
        if (torneo == null) {
            return tabla;
        }

        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            PosicionTorneo posicion = new PosicionTorneo(inscripcion.getClub());
            tabla.add(posicion);
        }
        for (Partido partido : torneo.getPartidos()) {
            if (partido.getEstado() != EstadoPartido.FINALIZADO) {
                continue;
            }
            int golesLocal = partido.getGolesLocal();
            int golesVisitante = partido.getGolesVisitante();

            for (PosicionTorneo posicion : tabla) {
                Club club = posicion.getClub();
                if (club.equals(partido.getLocal())) {
                    posicion.registrarResultado(golesLocal, golesVisitante);
                } else if (club.equals(partido.getVisitante())) {
                    posicion.registrarResultado(golesVisitante, golesLocal);
                }
            }
        }
        for (int i = 0; i < tabla.size() - 1; i++) {
            for (int j = i + 1; j < tabla.size(); j++) {
                PosicionTorneo primero = tabla.get(i);
                PosicionTorneo segundo = tabla.get(j);
                boolean intercambiar = false;
                if (segundo.getPuntos() > primero.getPuntos()) {
                    intercambiar = true;
                } else if (segundo.getPuntos() == primero.getPuntos()) {
                    if (segundo.calcularDiferenciaGol() > primero.calcularDiferenciaGol()) {
                        intercambiar = true;
                    } else if (segundo.calcularDiferenciaGol() == primero.calcularDiferenciaGol() && segundo.getGolesAFavor() > primero.getGolesAFavor()) {
                        intercambiar = true;
                    }
                }

                if (intercambiar) {
                    tabla.set(i, segundo);
                    tabla.set(j, primero);
                }
            }
        }
        return tabla;
    }

    public Map<Jugador, Integer> generarTablaGoleadores (Torneo torneo) {
        Map<Jugador,Integer> goleadores = new HashMap<>();
        if (torneo == null) {
            return goleadores;
        }

        for (Partido partido : torneo.getPartidos()) {
            if (partido.getEstado() != EstadoPartido.FINALIZADO) {
                continue;
            }
            for (Gol gol: partido.getGolesValido()) {
                if (gol.getTipo() == TipoGol.EN_CONTRA) {
                    continue;
                }
                Jugador jugador = gol.getJugador();
                int cantidad = 0;
                if (goleadores.containsKey(jugador)) {
                    cantidad = goleadores.get(jugador);
                }
                goleadores.put(jugador,cantidad+1);
            }

        }
        return goleadores;
    }

    public PosicionTorneo obtenerVallaMenosVencida(Torneo torneo) {
        List<PosicionTorneo> tabla = generarTablaPosiciones(torneo);
        PosicionTorneo mejor = null;
        double menorPromedio = Double.MAX_VALUE;
        for (PosicionTorneo posicion : tabla) {
            if (posicion.getPartidosJugados() == 0){
                continue;
            }
            double promedio = (double) posicion.getGolesEnContra() / posicion.getPartidosJugados();
            if (promedio < menorPromedio) {
                menorPromedio = promedio;
                mejor = posicion;
            }
        }
        return mejor;
    }

    public double generarProyeccion(Torneo torneo, Club club, ProyeccionClasificacion estrategia) {
        if (torneo == null || club == null || estrategia == null) {
            return 0;
        }

        return estrategia.calcularProbabilidad(torneo, club);
    }
}
