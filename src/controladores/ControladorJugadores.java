package controladores;
import clubes.Club;
import clubes.GestorClubes;
import clubes.GestorContratos;
import enums.Posicion;
import personas.Jugador;
import torneos.GestorTorneos;
import torneos.InscripcionTorneo;
import torneos.JugadorPlantel;
import torneos.Torneo;
import vistas.VistaConsola;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ControladorJugadores {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorContratos gestorContratos;
    private GestorClubes gestorClubes;
    public ControladorJugadores(VistaConsola vista, GestorTorneos gestorTorneos, GestorContratos gestorContratos,GestorClubes gestorClubes) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.gestorContratos = gestorContratos;
        this.gestorClubes = gestorClubes;
    }
    public void gestionarJugadores() {
        int opcion;
        do {
            vista.mostrarMenuJugadores();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    agregarJugadorPlantel();
                    break;
                case 2:
                    consultarJugadoresPlantel();
                    break;
                case 3:
                    agregarClub();
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
    private void consultarJugadoresPlantel() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        String nombreClub = vista.solicitarTexto("Nombre del club: ");
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            if (inscripcion.getClub().getNombre().equalsIgnoreCase(nombreClub)) {
                vista.mostrarMensaje("\n=== PLANTEL DE " + inscripcion.getClub().getNombre().toUpperCase() + " ===");
                List<JugadorPlantel> jugadores = inscripcion.getPlantel().getJugadoresPlantel();
                if (jugadores.isEmpty()) {
                    vista.mostrarMensaje("Este club todavia no tiene jugadores");
                    return;
                }
                for (JugadorPlantel jugador : jugadores) {
                    vista.mostrarMensaje(jugador.getJugador().getNombre() + " " + jugador.getJugador().getApellido());
                }
                return;
            }
        }
        vista.mostrarMensaje("No se encotró el club en el torneo.");
    }

    public void agregarClub() {
        String nombre = vista.solicitarTexto("Nombre del club: ");
        try {
            LocalDate fundacion = LocalDate.parse(vista.solicitarTexto("Fecha de fundación (AAAA-MM-DD): "));
            Club club = new Club(nombre, fundacion, null);
            if (gestorClubes.agregarClub(club)) {
                vista.mostrarMensaje("Club " + nombre + " creado correctamente.");
            } else {
                vista.mostrarMensaje("No se pudo crear el club.");
            }
        } catch (DateTimeParseException e) {
            vista.mostrarMensaje("Error: formato de fecha inválido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }

    }

    public void agregarJugadorPlantel() {
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("Primero debe crear un torneo.");
            return;
        }
        String nombreClub = vista.solicitarTexto("Nombre del club: ");
        InscripcionTorneo inscripcionEncontrada = null;
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            if (inscripcion.getClub().getNombre().equalsIgnoreCase(nombreClub)) {
                inscripcionEncontrada = inscripcion;
                break;
            }
        }
        if (inscripcionEncontrada == null) {
            vista.mostrarMensaje("El club no está inscripto en el torneo.");
            return;
        }
        try {
            int dni = Integer.parseInt(vista.solicitarTexto("DNI: "));
            String nombre = vista.solicitarTexto("Nombre: ");
            String apellido = vista.solicitarTexto("Apellido: ");
            LocalDate nacimiento = LocalDate.parse(vista.solicitarTexto("Fecha de Nacimiento (AAAA-MM-DD): "));
            Posicion posicion = Posicion.valueOf(vista.solicitarTexto("Posicion (ARQUERO, DEFENSOR, MEDIOCAMPISTA, DELANTERO): ").trim().toUpperCase());
            double valorMercado = Double.parseDouble(vista.solicitarTexto("Valor de mercado: "));
            int numeroCamiseta = Integer.parseInt(vista.solicitarTexto("Número de camiseta: "));
            Jugador jugador = new Jugador(dni, nombre, apellido, nacimiento, posicion, valorMercado);
            LocalDate inicioContrato = LocalDate.parse(vista.solicitarTexto("Inicio del contrato (AAAA-MM-DD): "));
            LocalDate finContrato = LocalDate.parse(vista.solicitarTexto("Fin del contrato (AAAA-MM-DD): "));
            double salario = Double.parseDouble(vista.solicitarTexto("Salario: "));
            gestorContratos.registrarAlta(jugador, inscripcionEncontrada.getClub(), inicioContrato, finContrato, salario);
            boolean agregado = inscripcionEncontrada.agregarJugador(numeroCamiseta, jugador);
            if (agregado) {
                vista.mostrarMensaje("Jugador agregado correctamente.");
            } else {
                vista.mostrarMensaje("No se pudo agregar el jugador al plantel.");
            }
        } catch (DateTimeParseException e) {
            vista.mostrarMensaje("Error: formato de fecha inválido-");
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: ingresaste un número inválido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }
}
