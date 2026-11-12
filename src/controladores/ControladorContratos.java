package controladores;

import clubes.Club;
import clubes.Contrato;
import clubes.GestorContratos;
import personas.Jugador;
import torneos.GestorTorneos;
import torneos.InscripcionTorneo;
import torneos.JugadorPlantel;
import torneos.Torneo;
import vistas.VistaConsola;
import enums.EstadoTorneo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ControladorContratos {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorContratos gestorContratos;

    public ControladorContratos(VistaConsola vista, GestorTorneos gestorTorneos, GestorContratos gestorContratos) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.gestorContratos = gestorContratos;
    }

    public void gestionarContratos() {
        int opcion;
        do {
            vista.mostrarMenuContratos();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    consultarContratos();
                    break;
                case 2:
                    rescindirContrato();
                    break;

                case 3:
                    transferirJugador();
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
    private Jugador buscarJugador() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return null;
        }
        int dni;
        try {
            dni = Integer.parseInt(vista.solicitarTexto("DNI del jugador: "));
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("DNI inválido.");
            return null;
        }
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            for (JugadorPlantel jp :
                    inscripcion.getPlantel().getJugadoresPlantel()) {
                if (jp.getJugador().getDni() == dni) {
                    return jp.getJugador();
                }
            }
        }
        vista.mostrarMensaje("No se encontró el jugador.");
        return null;
    }
    private void consultarContratos() {
        Jugador jugador = buscarJugador();
        if (jugador == null) {
            return;
        }
        if (jugador.getContratos().isEmpty()) {
            vista.mostrarMensaje("El jugador no tiene contratos.");
            return;
        }
        vista.mostrarMensaje("\n=== CONTRATOS DEL JUGADOR ===");
        for (Contrato contrato : jugador.getContratos()) {
            vista.mostrarMensaje("Club: " + contrato.getClub().getNombre());
            vista.mostrarMensaje("Inicio: " + contrato.getFechaInicio());
            vista.mostrarMensaje("Fin: " + contrato.getFechaFin());
            vista.mostrarMensaje("Sueldo: $" + contrato.getSueldo());
            vista.mostrarMensaje("Vigente: " + contrato.estaVigente());
            vista.mostrarMensaje("------------------------");
        }
    }

    private void rescindirContrato() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo != null && torneo.getEstado() != EstadoTorneo.PLANIFICADO) {
            vista.mostrarMensaje("No se pueden rescindir contratos durante o después del torneo");
            return;
        }
        Jugador jugador = buscarJugador();
        if (jugador == null) {
            return;
        }
        boolean rescindido = gestorContratos.rescindirContrato(jugador);
        if (rescindido) {
            vista.mostrarMensaje("Contrato rescindido correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo rescindir el contrato.");
        }
    }

    private void transferirJugador() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        if (torneo.getEstado() != EstadoTorneo.PLANIFICADO) {
            vista.mostrarMensaje("No se permiten transferencias fuera de la etapa de planificacion.");
            return;
        }
        Jugador jugador = buscarJugador();
        if (jugador == null) {
            return;
        }
        String nombreClub = vista.solicitarTexto("Nombre del club de destino: ");
        Club clubDestino = null;
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            if (inscripcion.getClub().getNombre().equalsIgnoreCase(nombreClub)) {
                clubDestino = inscripcion.getClub();
                break;
            }
        }
        if (clubDestino == null) {
            vista.mostrarMensaje("No se encontró el club de destino.");
            return;
        }
        try {
            LocalDate fechaFin = LocalDate.parse(vista.solicitarTexto("Fin del nuevo contrato (AAAA-MM-DD): "));
            double sueldo = Double.parseDouble(vista.solicitarTexto("Nuevo sueldo: "));
            boolean transferido = gestorContratos.transferirJugador(jugador, clubDestino, fechaFin, sueldo);
            if (transferido) {
                vista.mostrarMensaje("Jugador transferido correctamente.");
            } else {
                vista.mostrarMensaje("No se pudo transferir el jugador.");
            }
        } catch (DateTimeParseException e) {
            vista.mostrarMensaje("Fecha inválida.");
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Sueldo inválido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }
}
