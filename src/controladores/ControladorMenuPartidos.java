package controladores;
import enums.TipoGol;
import partidos.Partido;
import personas.Jugador;
import torneos.GestorTorneos;
import torneos.Torneo;
import vistas.VistaConsola;
import partidos.Estadio;
import personas.Arbitro;
import enums.CategoriaArbitro;
import enums.TipoCesped;
import enums.ZonaRemate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;
import enums.RolEquipo;
import torneos.InscripcionTorneo;
import torneos.JugadorPlantel;
import torneos.Plantel;
import partidos.NominaPartido;
import clubes.Club;

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
                    convocarJugador();
                    break;
                case 4:
                    iniciarPartido();
                    break;
                case 5:
                    registrarGol();
                    break;
                case 6:
                    registrarTarjetaAmarilla();
                    break;
                case 7:
                    registrarTarjetaRoja();
                    break;
                case 8:
                    registrarCambio();
                    break;
                case 9:
                    finalizarPartido();
                    break;
                case 10:
                    registrarRemate();
                    break;

                case 11:
                    registrarFalta();
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
            int dniPrincipal = Integer.parseInt(vista.solicitarTexto("DNI del arbitro principal:"));
            int dniAsistente1 = Integer.parseInt(vista.solicitarTexto("DNI del primer asistente:"));
            int dniAsistente2 = Integer.parseInt(vista.solicitarTexto("DNI del segundo asistente:"));
            Arbitro arbitroPrincipal = new Arbitro(dniPrincipal,nombrePrincipal,"Principal",LocalDate.of(1980,1,1),CategoriaArbitro.NACIONAL,null,"AFA");
            Arbitro asistente1 = new Arbitro(dniAsistente1,nombreAsistente1,"Asistente",LocalDate.of(1981,1,1),CategoriaArbitro.NACIONAL,null,"AFA");
            Arbitro asistente2 = new Arbitro(dniAsistente2,nombreAsistente2,"Asistente",LocalDate.of(1982,1,1), CategoriaArbitro.NACIONAL,null,"AFA");
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
    private void convocarJugador() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Torneo torneo = gestorTorneos.getTorneoActual();
        vista.mostrarMensaje("\n=== CONVOCAR JUGADOR ===");
        vista.mostrarMensaje("1. Equipo local");
        vista.mostrarMensaje("2. Equipo visitante");
        int opcionEquipo = vista.solicitarOpcion();
        RolEquipo rol;
        Club club;
        if (opcionEquipo == 1) {
            rol = RolEquipo.LOCAL;
            club = partido.getLocal();

        } else if (opcionEquipo == 2) {
            rol = RolEquipo.VISITANTE;
            club = partido.getVisitante();

        } else {
            vista.mostrarMensaje("Equipo inválido.");
            return;
        }
        InscripcionTorneo inscripcionEncontrada = null;
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            if (inscripcion.getClub().equals(club)) {
                inscripcionEncontrada = inscripcion;
                break;
            }
        }
        if (inscripcionEncontrada == null) {
            vista.mostrarMensaje("No se encontró la inscripción del club.");
            return;
        }
        Plantel plantel = inscripcionEncontrada.getPlantel();
        List<JugadorPlantel> jugadores = plantel.getJugadoresPlantel();
        if (jugadores.isEmpty()) {
            vista.mostrarMensaje("El plantel no tiene jugadores.");
            return;
        }
        vista.mostrarMensaje("\n=== JUGADORES DISPONIBLES ===");
        for (int i = 0; i < jugadores.size(); i++) {
            JugadorPlantel jp = jugadores.get(i);
            vista.mostrarMensaje((i + 1) + ". " + jp.getJugador().getNombre() + " " + jp.getJugador().getApellido() + (jp.estaSuspendido() ? " (SUSPENDIDO)" : "")
            );
        }
        vista.mostrarMensaje("Seleccione el número del jugador:");
        int numeroJugador = vista.solicitarOpcion();
        if (numeroJugador < 1 || numeroJugador > jugadores.size()) {
            vista.mostrarMensaje("Jugador inválido.");
            return;
        }
        JugadorPlantel jugador = jugadores.get(numeroJugador - 1);
        boolean existeNomina = false;
        for (NominaPartido nomina : partido.getNominas()) {
            if (nomina.getRol() == rol) {
                existeNomina = true;
                break;
            }
        }
        if (!existeNomina) {
            partido.agregarNomina(rol, club);
        }
        vista.mostrarMensaje("\n1. Titular");
        vista.mostrarMensaje("2. Suplente");
        int tipoConvocatoria = vista.solicitarOpcion();
        boolean convocado;
        if (tipoConvocatoria == 1) {
            convocado = torneo.convocarTitular(partido, rol, jugador);
        } else if (tipoConvocatoria == 2) {
            convocado = torneo.convocarSuplente(partido, rol, jugador);
        } else {
            vista.mostrarMensaje("Opción inválida.");
            return;
        }
        if (convocado) {
            vista.mostrarMensaje("Jugador convocado correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo convocar al jugador. " + "Verificá su disponibilidad y las reglas de convocatoria.");
        }
    }

    private void iniciarPartido() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        boolean iniciado = partido.iniciar();
        if (iniciado) {
            vista.mostrarMensaje("Partido iniciado correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo iniciar el partido. Verificá que esté programado y tenga las nóminas correspondientes.");
        }
    }

    private Jugador seleccionarJugadorConvocado(Partido partido) {
        List<Jugador> jugadores = new ArrayList<>();
        for (NominaPartido nomina : partido.getNominas()) {
            for (Jugador jugador : nomina.getJugadoresTitulares()) {
                jugadores.add(jugador);
            }
            for (Jugador jugador : nomina.getJugadoresSuplentes()) {
                jugadores.add(jugador);
            }
        }
        if (jugadores.isEmpty()) {
            vista.mostrarMensaje("No hay jugadores convocados.");
            return null;
        }
        vista.mostrarMensaje("\n=== JUGADORES CONVOCADOS ===");
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            vista.mostrarMensaje((i + 1) + ". " + jugador.getNombre() + " " + jugador.getApellido());
        }
        vista.mostrarMensaje("Seleccione el jugador:");
        int numero = vista.solicitarOpcion();
        if (numero < 1 || numero > jugadores.size()) {
            vista.mostrarMensaje("Jugador inválido.");
            return null;
        }
        return jugadores.get(numero - 1);
    }
    private void registrarGol() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Jugador jugador = seleccionarJugadorConvocado(partido);
        if (jugador == null) {
            return;
        }
        vista.mostrarMensaje("Minuto del gol:");
        int minuto = vista.solicitarOpcion();
        boolean registrado = controladorPartidos.registrarGol(partido, minuto, jugador, TipoGol.A_FAVOR);
        vista.mostrarMensaje(registrado ? "Gol registrado correctamente." : "No se pudo registrar el gol.");
    }

    private void registrarTarjetaRoja() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Jugador jugador = seleccionarJugadorConvocado(partido);
        if (jugador == null) {
            return;
        }
        vista.mostrarMensaje("Minuto de la expulsión:");
        int minuto = vista.solicitarOpcion();
        boolean registrado = controladorPartidos.registrarTarjetaRoja(partido, minuto, jugador
        );
        vista.mostrarMensaje(registrado ? "Tarjeta roja registrada correctamente." : "No se pudo registrar la tarjeta roja.");
    }

    private void finalizarPartido() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Torneo torneo = gestorTorneos.getTorneoActual();
        boolean finalizado = controladorPartidos.finalizarPartido(torneo, partido
        );
        if (finalizado) {
            vista.mostrarMensaje("Partido finalizado correctamente.");
            vista.mostrarMensaje("Resultado: " + partido.getLocal().getNombre() + " " + partido.getGolesLocal() + " - " + partido.getGolesVisitante() + " " + partido.getVisitante().getNombre()
            );
        } else {
            vista.mostrarMensaje("No se pudo finalizar el partido.");
        }
    }

    public void registrarCambio() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        vista.mostrarMensaje("\n=== JUGADOR QUE SALE ===");
        Jugador jugadorSale = seleccionarJugadorConvocado(partido);
        if (jugadorSale == null) {
            return;
        }
        vista.mostrarMensaje("\n=== JUGADOR QUE ENTRA ===");
        Jugador jugadorEntra = seleccionarJugadorConvocado(partido);
        if (jugadorEntra == null) {
            return;
        }
        vista.mostrarMensaje("Minuto del cambio: ");
        int minuto = vista.solicitarOpcion();
        boolean registrado = controladorPartidos.registrarCambio(partido,minuto,jugadorSale,jugadorEntra);
        if (registrado) {
            vista.mostrarMensaje("Cambio registrado correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo registrar el cambio.");
        }
    }

    public void registrarTarjetaAmarilla() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Jugador jugador = seleccionarJugadorConvocado(partido);
        if (jugador == null) {
            return;
        }
        vista.mostrarMensaje("Minutp de la tarjeta amarilla: ");
        int minuto = vista.solicitarOpcion();
        boolean registrado = controladorPartidos.registrarTarjetaAmarilla(partido,minuto,jugador);
        if (registrado) {
            vista.mostrarMensaje("Tarjeta Amarilla registrada correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo registrar la tarjeta amarilla.");
        }
    }

    public void registrarRemate() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Jugador jugador = seleccionarJugadorConvocado(partido);
        if (jugador == null) {
            return;
        }
        vista.mostrarMensaje("\n=== ZONA DEL REMATE ===");
        vista.mostrarMensaje("1. Area chica");
        vista.mostrarMensaje("2. Area grande");
        vista.mostrarMensaje("3. Fuera del area");
        vista.mostrarMensaje("4. Penal");
        int opcion = vista.solicitarOpcion();
        ZonaRemate zona;
        switch (opcion) {
            case 1:
                zona=ZonaRemate.AREA_CHICA;
                break;
            case 2:
                zona =ZonaRemate.AREA_GRANDE;
                break;
            case 3:
                zona= ZonaRemate.FUERA_DEL_AREA;
                break;
            case 4:
                zona =ZonaRemate.PENAL;
                break;
            default:
                vista.mostrarMensaje("Zona de remate invalida.");
                return;
        }
        vista.mostrarMensaje("Mnuto del remate:");
        int minuto = vista.solicitarOpcion();
        if (minuto < 0 || minuto > 120) {
            vista.mostrarMensaje("El minuto debe estar entre 0 y 120.");
            return;
        }
        boolean registrado = controladorPartidos.registrarRemate(partido,minuto,jugador,zona);
        if (registrado) {
            vista.mostrarMensaje("Remate registrado correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo registrar el remate.");
        }
    }

    private void registrarFalta() {
        Partido partido = seleccionarPartido();
        if (partido == null) {
            return;
        }
        Jugador jugador = seleccionarJugadorConvocado(partido);
        if (jugador == null) {
            return;
        }
        vista.mostrarMensaje("Minuto de la falta:");
        int minuto = vista.solicitarOpcion();
        if (minuto < 0 || minuto > 120) {
            vista.mostrarMensaje("El minuto debe estar entre 0 y 120.");
            return;
        }
        boolean registrado = controladorPartidos.registrarFalta(partido, minuto, jugador);
        if (registrado) {
            vista.mostrarMensaje("Falta registrada correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo registrar la falta.");
        }
    }


}
