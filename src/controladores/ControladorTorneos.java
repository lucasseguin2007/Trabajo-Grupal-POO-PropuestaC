package controladores;
import clubes.Club;
import torneos.GestorTorneos;
import torneos.InscripcionTorneo;
import torneos.Torneo;
import vistas.VistaConsola;
import clubes.GestorClubes;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


public class ControladorTorneos {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorClubes gestorClubes;
    public ControladorTorneos(VistaConsola vista, GestorTorneos gestorTorneos, GestorClubes gestorClubes) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.gestorClubes = gestorClubes;
    }
    public void gestionarTorneos() {
        int opcion;
        do {
            vista.mostrarMenuTorneo();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    crearTorneo();
                    break;
                case 2:
                    consultarTorneoActual();
                    break;
                case 3:
                    consultarTorneosAnteriores();
                    break;
                case 4:
                    inscribirClub();
                    break;
                case 5:
                    consultarClubesInscriptos();
                    break;
                case 6:
                    confirmarInscripcionClub();
                    break;
                case 7:
                    generarFixture();
                    break;
                case 8:
                    iniciarTorneo();
                    break;

                case 9:
                    finalizarTorneo();
                    break;

                case 10:
                    archivarTorneo();
                    break;
                case 0:
                    vista.mostrarMensaje("Volviendo al menú principal...");
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }
    private void crearTorneo() {
        if (gestorTorneos.getTorneoActual() != null) {
            vista.mostrarMensaje("Ya existe un torneo actual. Debe finalizarse y archivarse antes de crear otro.");
            return;
        }
        String nombre = vista.solicitarTexto("Nombre del torneo: ");

        try {
            LocalDate fechaInicio = LocalDate.parse(vista.solicitarTexto("Fecha de inicio (AAAA-MM-DD): "));
            LocalDate fechaFin = LocalDate.parse(vista.solicitarTexto("Fecha de fin (AAAA-MM-DD): "));
            Torneo torneo = new Torneo(nombre, fechaInicio, fechaFin);
            boolean creado = gestorTorneos.iniciarNuevoTorneo(torneo);
            if (creado) {
                vista.mostrarMensaje("Torneo creado correctamente.");
            } else {
                vista.mostrarMensaje("No se pudo crear el torneo.");
            }
        } catch (DateTimeParseException e) {
            vista.mostrarMensaje("Error: formato de fecha inválido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    private void consultarTorneoActual() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }

        vista.mostrarMensaje("\n=== TORNEO ACTUAL ===");
        vista.mostrarMensaje("Nombre: " + torneo.getNombre());
        vista.mostrarMensaje("Estado: " + torneo.getEstado());
    }

    private void consultarTorneosAnteriores() {
        List<Torneo> historial = gestorTorneos.getHistorialTorneos();
        if (historial.isEmpty()) {
            vista.mostrarMensaje("No hay torneos anteriores");
            return;
        }
        vista.mostrarMensaje("\n=== HISTORIAL DE TORNEOS ===");
        for (Torneo torneo: historial) {
            vista.mostrarMensaje(
                    "Nombre: " + torneo.getNombre()
                            + " | Estado: " + torneo.getEstado()
            );
        }
    }
    public void inscribirClub() {
        Torneo torneo = gestorTorneos.getTorneoActual();

        if (torneo == null) {
            vista.mostrarMensaje("Primero debe crear un torneo.");
            return;
        }
        String nombreClub = vista.solicitarTexto("Nombre del club: ");
        Club club = gestorClubes.buscarClub(nombreClub);
        if (club == null) {
            vista.mostrarMensaje("El club no existe en el registro.");
            return;
        }
        boolean inscripto = torneo.inscribirClub(club);
        if (inscripto) {
            vista.mostrarMensaje("Club " + nombreClub + " inscripto correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo inscribir el club.");
        }
    }

    public void consultarClubesInscriptos() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        List<InscripcionTorneo> inscripciones = torneo.getInscriptos();
        if (inscripciones.isEmpty()) {
            vista.mostrarMensaje("Todavía no hay clubes inscriptos.");
            return;
        }

        vista.mostrarMensaje("\n=== CLUBES INSCRIPTOS ===");
        for (InscripcionTorneo inscripcion: inscripciones) {
            vista.mostrarMensaje("Club: " + inscripcion.getClub().getNombre() + " | Confirmada: " + inscripcion.estaConfirmada());
        }
    }

    private void confirmarInscripcionClub() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        String nombreClub = vista.solicitarTexto("Nombre del club: ");
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            if (inscripcion.getClub().getNombre().equalsIgnoreCase(nombreClub)) {
                boolean confirmada = inscripcion.confirmarInscripcion();
                if (confirmada) {
                    vista.mostrarMensaje("Inscripción de " + inscripcion.getClub().getNombre() + " confirmada correctamente.");
                } else {
                    vista.mostrarMensaje("No se pudo confirmar la inscripción. " + "Verificá que el plantel cumpla los requisitos.");
                }
                return;
            }
        }
        vista.mostrarMensaje("El club no está inscripto en el torneo.");
    }

    private void generarFixture() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        try {
            torneo.generarFixture();
            vista.mostrarMensaje("Fixture generado.");
            vista.mostrarMensaje("Cantidad de partidos: " + torneo.getPartidos().size());
        } catch (IllegalArgumentException | IllegalStateException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

    public void iniciarTorneo() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        boolean iniciado = torneo.iniciarTorneo();
        if (iniciado) {
            vista.mostrarMensaje("Torneo iniciado correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo iniciar el torneo. Verifique el fixture");
        }
    }

    public void finalizarTorneo() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        boolean finalizado = torneo.finalizarTorneo();
        if (finalizado) {
            vista.mostrarMensaje("Torneo finalizado correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo finalizar el torneo. Verifique que los partidos hayan terminado");
        }
    }

    public void archivarTorneo() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        boolean archivado = gestorTorneos.archivarTorneoActual();
        if (archivado) {
            vista.mostrarMensaje("Torneo archivado. Ya puede crear uno nuevo");
        } else{
            vista.mostrarMensaje("No se pudo archivar el torneo. Debe esta finalizado.");
        }
    }

}
