package controladores;

import reportes.GestorReportes;
import reportes.PosicionTorneo;
import torneos.GestorTorneos;
import torneos.InscripcionTorneo;
import torneos.Torneo;
import vistas.VistaConsola;
import clubes.Club;
import personas.Jugador;
import reportes.ProyeccionClasificacion;
import reportes.ProyeccionPorPromedio;
import reportes.ProyeccionPorSimulacionXG;

import java.util.Map;
import java.util.List;

public class ControladorReportes {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorReportes gestorReportes;
    public ControladorReportes(VistaConsola vista, GestorTorneos Torneo) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.gestorReportes = new GestorReportes();
    }

    public void gestionarReportes() {
        int opcion;
        do {
            vista.mostrarMenuReportes();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    mostrarTablaPosiciones();
                    break;
                case 2:
                    mostrarTablaGoleadores();
                    break;
                case 3:
                    mostrarVallaMenosVencida();
                    break;
                case 4:
                    mostrarProyeccion(new ProyeccionPorPromedio(30));
                    break;
                case 5:
                    mostrarProyeccion(new ProyeccionPorSimulacionXG(30));
                    break;
                case 0:
                    vista.mostrarMensaje("Volviendo al menú principal...");
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida.");
                    break;
            }
        }while (opcion != 0);
    }
    private void mostrarTablaPosiciones() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo ahora.");
            return;
        }
        List<PosicionTorneo> tabla = gestorReportes.generarTablaPosiciones(torneo);
        if (tabla.isEmpty()) {
            vista.mostrarMensaje("No hay posiciones disponibles.");
            return;
        }
        vista.mostrarMensaje("\n=== TABLA DE POSICIONES ===");
        for (int i = 0; i < tabla.size(); i++) {
            PosicionTorneo posicion = tabla.get(i);
            vista.mostrarMensaje((i + 1) + ". " + posicion.getClub().getNombre() + " | Puntos: " + posicion.getPuntos()
            );
        }
    }
    private void mostrarTablaGoleadores() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        Map<Jugador, Integer> goleadores = gestorReportes.generarTablaGoleadores(torneo);
        if (goleadores.isEmpty()) {
            vista.mostrarMensaje("Todavía no hay goleadores registrados.");
            return;
        }
        vista.mostrarMensaje("\n=== TABLA DE GOLEADORES ===");
        for (Map.Entry<Jugador, Integer> entrada : goleadores.entrySet()) {
            Jugador jugador = entrada.getKey();
            int goles = entrada.getValue();
            vista.mostrarMensaje(jugador.getNombre() + " " + jugador.getApellido() + " | Goles: " + goles
            );
        }
    }
    private void mostrarVallaMenosVencida() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        PosicionTorneo posicion = gestorReportes.obtenerVallaMenosVencida(torneo);
        if (posicion == null) {
            vista.mostrarMensaje("No hay datos suficientes.");
            return;
        }
        vista.mostrarMensaje("\n=== VALLA MENOS VENCIDA ===");
        vista.mostrarMensaje("Club: " + posicion.getClub().getNombre());
        vista.mostrarMensaje("Goles en contra: " + posicion.getGolesEnContra());
    }
    private void mostrarProyeccion(ProyeccionClasificacion estrategia) {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        String nombreClub = vista.solicitarTexto("Nombre del club: ");
        Club clubEncontrado = null;
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            if (inscripcion.getClub().getNombre().equalsIgnoreCase(nombreClub)) {
                clubEncontrado = inscripcion.getClub();
                break;
            }
        }
        if (clubEncontrado == null) {
            vista.mostrarMensaje("El club no participa del torneo.");
            return;
        }
        double probabilidad = gestorReportes.generarProyeccion(torneo, clubEncontrado, estrategia);
        vista.mostrarMensaje("Proyección de " + clubEncontrado.getNombre() + ": " + (probabilidad * 100) + "%");
    }
}
