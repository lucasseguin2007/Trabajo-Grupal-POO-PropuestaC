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
import personas.Entrenador;
import enums.EspecialidadEntrenador;

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
                case 4:
                    asignarEntrenador();
                    break;

                case 5:
                    consultarEntrenador();
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
            boolean contratoRegistrado = gestorContratos.registrarAlta(jugador, inscripcionEncontrada.getClub(), inicioContrato, finContrato, salario);
            if (!contratoRegistrado) {
                vista.mostrarMensaje("No se pudo registrar el contrato del jugador");
                return;
            }
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
    public void asignarEntrenador() {
        String nombreClub = vista.solicitarTexto("Nombre del club:");
        Club club = gestorClubes.buscarClub(nombreClub);
        if (club == null) {
            vista.mostrarMensaje("El club no existe.");
            return;
        }

        try {
            int dni = Integer.parseInt(vista.solicitarTexto("DNI:"));
            String nombre = vista.solicitarTexto("Nombre:");
            String apellido = vista.solicitarTexto("Apellido:");
            LocalDate nacimiento = LocalDate.parse(vista.solicitarTexto("Fecha de nacimiento (AAAA-MM-DD):"));
            vista.mostrarMensaje("\n=== ESPECIALIDAD ===");
            vista.mostrarMensaje("1. Director tecnico");
            vista.mostrarMensaje("2. Preparador fisico");
            vista.mostrarMensaje("3. Ayudante");
            int opcion = vista.solicitarOpcion();
            EspecialidadEntrenador especialidad;
            switch (opcion) {
                case 1:
                    especialidad = EspecialidadEntrenador.DIRECTOR_TECNICO;
                    break;
                case 2:
                    especialidad = EspecialidadEntrenador.PREPARADOR_FISICO;
                    break;
                case 3:
                    especialidad = EspecialidadEntrenador.AYUDANTE;
                    break;
                default:
                    vista.mostrarMensaje("Especialidad invalida.");
                    return;
            }
            String licencia = vista.solicitarTexto("Licencia:");
            Entrenador entrenador = new Entrenador(dni,nombre,apellido,nacimiento,especialidad,licencia);
            if (club.asignarEntrenador(entrenador)) {
                boolean guardado = gestorClubes.guardarCambios();
                if (guardado) {
                    vista.mostrarMensaje("Entrenador asignado y guardado correctamente");
                } else {
                    vista.mostrarMensaje("Entrenador asignado pero no se pudo guardar en el archivo.");
                }
            } else {
                vista.mostrarMensaje("No se pudo asignar el entrenador");
            }
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("DNI invalido.");
        } catch (DateTimeParseException e) {
            vista.mostrarMensaje("Fecha invalida.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }
    public void consultarEntrenador() {
        String nombreClub = vista.solicitarTexto("Nombre del club: ");
        Club club = gestorClubes.buscarClub(nombreClub);
        if (club == null) {
            vista.mostrarMensaje("El club no existe.");
            return;
        }
        Entrenador entrenador = club.getEntrenador();

        if (entrenador == null) {
            vista.mostrarMensaje("El club no tiene entrenador asignado.");
            return;
        }
        vista.mostrarMensaje("\n=== ENTRENADOR DEL CLUB ===");
        vista.mostrarMensaje("Club: " + club.getNombre());
        vista.mostrarMensaje("Nombre: " + entrenador.getNombre());
        vista.mostrarMensaje("Apellido: " + entrenador.getApellido());
        vista.mostrarMensaje("DNI: " + entrenador.getDni());
        vista.mostrarMensaje("Especialidad: " + entrenador.getEspecialidad());
        vista.mostrarMensaje("Licencia: " + entrenador.getLicencia());
    }
}
