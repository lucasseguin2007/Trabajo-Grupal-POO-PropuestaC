package controladores;
import partidos.Partido;
import torneos.GestorTorneos;
import torneos.Torneo;
import vistas.VistaConsola;
import partidos.Estadio;
import personas.Arbitro;
import enums.CategoriaArbitro;
import enums.TipoCesped;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import java.util.List;

public class ControladorMenuPartidos {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private ControladorPartidos controladorPartidos;

    public ControladorMenuPartidos(VistaConsola vista, GestorTorneos gestorTorneos, ControladorPartidos controladorPartidos) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.controladorPartidos = controladorPartidos;
    }
    public void gestionarPartidos() {
        int opcion;
        do {
            vista.mostrarMenuPartidos();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    consultarFixture();
                    break;
                case 2:
                    programarPartido();
                    break;
                case 3:
                    vista.mostrarMensaje("Convocar jugadores.");
                    break;
                case 4:
                    vista.mostrarMensaje("Iniciar partido.");
                    break;
                case 5:
                    vista.mostrarMensaje("Registrar gol.");
                    break;
                case 6:
                    vista.mostrarMensaje("Registrar tarjeta roja.");
                    break;
                case 7:
                    vista.mostrarMensaje("Finalizar partido.");
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
    private void consultarFixture() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        List<Partido> partidos = torneo.getPartidos();
        if (partidos.isEmpty()) {
            vista.mostrarMensaje("Todavía no se generó el fixture.");
            return;
        }
        vista.mostrarMensaje("\n===== FIXTURE =====");
        for (int i = 0; i < partidos.size(); i++) {
            Partido partido = partidos.get(i);
            vista.mostrarMensaje((i + 1) + ". " + partido.getLocal().getNombre() + " vs " + partido.getVisitante().getNombre() + " | Estado: " + partido.getEstado());
        }
    }
    private Partido seleccionarPartido() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return null;
        }
        List<Partido> partidos = torneo.getPartidos();
        if (partidos.isEmpty()) {
            vista.mostrarMensaje("No hay partidos en el fixture.");
            return null;
        }
        consultarFixture();
        int numero = vista.solicitarOpcion();
        if (numero < 1 || numero > partidos.size()) {
            vista.mostrarMensaje("Número de partido inválido.");
            return null;
        }
        return partidos.get(numero - 1);
    }
    private void programarPartido() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Torneo torneo = gestorTorneos.getTorneoActual();
        try {
            // Fecha y hora
            LocalDateTime fechaHora = LocalDateTime.parse(vista.solicitarTexto("Fecha y hora (AAAA-MM-DDTHH:MM): "));
            String nombreEstadio = vista.solicitarTexto("Nombre del estadio: ");
            int capacidad = Integer.parseInt(vista.solicitarTexto("Capacidad: "));
            String ciudad = vista.solicitarTexto("Ciudad: ");
            Estadio estadio = new Estadio(nombreEstadio, capacidad, "Templado", TipoCesped.NATURAL, "Calle Principal", 100, ciudad, "Buenos Aires");

            String nombrePrincipal = vista.solicitarTexto("Nombre del árbitro principal: ");

            String nombreAsistente1 = vista.solicitarTexto("Nombre del primer asistente: ");

            String nombreAsistente2 = vista.solicitarTexto("Nombre del segundo asistente: ");
            Arbitro arbitroPrincipal = new Arbitro(40000001, nombrePrincipal, "Principal", LocalDate.of(1980, 1, 1), CategoriaArbitro.NACIONAL, null, "AFA");
            Arbitro asistente1 = new Arbitro(40000002, nombreAsistente1, "Asistente", LocalDate.of(1981, 1, 1), CategoriaArbitro.NACIONAL, null, "AFA");
            Arbitro asistente2 = new Arbitro(40000003, nombreAsistente2, "Asistente", LocalDate.of(1982, 1, 1), CategoriaArbitro.NACIONAL, null, "AFA");
            boolean programado = partido.programarPartido(
                    torneo,
                    fechaHora,
                    estadio,
                    arbitroPrincipal,
                    asistente1,
                    asistente2
            );
            if (programado) {
                vista.mostrarMensaje(
                        "Partido programado correctamente."
                );
            } else {
                vista.mostrarMensaje(
                        "No se pudo programar el partido."
                );
            }
        } catch (DateTimeParseException e) {
            vista.mostrarMensaje("Error: fecha u hora inválida.");
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: número inválido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }

}
