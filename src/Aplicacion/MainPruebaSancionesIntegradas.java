package Aplicacion;

import clubes.Club;
import clubes.GestorContratos;
import enums.*;
import partidos.Estadio;
import partidos.Partido;
import personas.Arbitro;
import personas.Jugador;
import torneos.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import controladores.ControladorPartidos;
import notificaciones.*;

import java.util.ArrayList;
import java.util.List;

public class MainPruebaSancionesIntegradas {

    public static void main(String[] args) {

        // 1. Crear torneo y clubes
        Torneo torneo = new Torneo(
                "Liga de prueba",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusMonths(3)
        );

        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Club river = new Club("River", LocalDate.of(1901, 5, 25), null);
        Club racing = new Club("Racing", LocalDate.of(1903, 3, 25), null);
        Club independiente = new Club("Independiente", LocalDate.of(1905, 1, 1), null);
        // Canales disponibles
        List<CanalEnvio> canales = new ArrayList<>();
        canales.add(new CanalApp());

        ServicioNotificaciones servicio =
                new ServicioNotificaciones(canales);

        ControladorPartidos controlador =
                new ControladorPartidos(servicio);

// Suscriptor de Boca
        Suscriptor lucas = new Suscriptor(
                "Lucas",
                "lucas@gmail.com",
                "1123456789"
        );

        lucas.seguirClub(boca);
        lucas.configurarCanal(new CanalApp());

// Suscriptor de River
        Suscriptor juan = new Suscriptor(
                "Juan",
                "juan@gmail.com",
                "1198765432"
        );

        juan.seguirClub(river);
        juan.configurarCanal(new CanalApp());

// Suscriptor de Racing
        Suscriptor pedro = new Suscriptor(
                "Pedro",
                "pedro@gmail.com",
                "1155555555"
        );

        pedro.seguirClub(racing);
        pedro.configurarCanal(new CanalApp());

// Registrar suscriptores
        controlador.agregarSuscriptor(lucas);
        controlador.agregarSuscriptor(juan);
        controlador.agregarSuscriptor(pedro);
        torneo.inscribirClub(boca);
        torneo.inscribirClub(river);
        torneo.inscribirClub(racing);
        torneo.inscribirClub(independiente);

        // 2. Agregar 18 jugadores a cada plantel
        GestorContratos gestor = new GestorContratos();
        int dni = 30000000;

        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {

            for (int i = 1; i <= 18; i++) {

                Jugador jugador = new Jugador(
                        dni++,
                        "Jugador" + i,
                        inscripcion.getClub().getNombre(),
                        LocalDate.of(2000, 1, 1),
                        Posicion.DEFENSOR,
                        100000
                );

                gestor.registrarAlta(
                        jugador,
                        inscripcion.getClub(),
                        LocalDate.now().minusDays(1),
                        LocalDate.now().plusYears(1),
                        100000
                );

                inscripcion.agregarJugador(i, jugador);
            }

            System.out.println(
                    inscripcion.getClub().getNombre()
                            + " confirmado: "
                            + inscripcion.confirmarInscripcion()
            );
        }

        // 3. Generar fixture y elegir el primer partido
        torneo.generarFixture();

        Partido partido = null;

        for (Partido p : torneo.getPartidos()) {
            if (p.getLocal() == boca && p.getVisitante() == river) {
                partido = p;
                break;
            }
        }

        if (partido == null) {
            System.out.println("No se encontró Boca vs River.");
            return;
        }

        System.out.println("\nPartido: "
                + partido.getLocal().getNombre()
                + " vs "
                + partido.getVisitante().getNombre());

        // 4. Obtener los planteles del partido
        Plantel plantelLocal = null;
        Plantel plantelVisitante = null;

        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {

            if (inscripcion.getClub() == partido.getLocal()) {
                plantelLocal = inscripcion.getPlantel();
            }

            if (inscripcion.getClub() == partido.getVisitante()) {
                plantelVisitante = inscripcion.getPlantel();
            }
        }

        if (plantelLocal == null || plantelVisitante == null) {
            System.out.println("No se encontraron los planteles.");
            return;
        }

        // 5. Elegir un jugador que ya tiene una suspensión
        JugadorPlantel suspendido =
                plantelLocal.getJugadoresPlantel().get(0);

        Sancion sancionAnterior = new Sancion(
                1,
                MotivoSancion.EXPULSION,
                suspendido,
                partido.getLocal()
        );

        suspendido.agregarSancion(sancionAnterior);

        System.out.println("\n=== SANCIÓN ANTERIOR ===");
        System.out.println("Suspendido antes del partido: "
                + suspendido.estaSuspendido());

        // 6. Crear estadio y árbitros
        Estadio estadio = new Estadio(
                "Estadio de prueba",
                50000,
                "Templado",
                TipoCesped.NATURAL,
                "Calle Principal",
                100,
                "Buenos Aires",
                "Buenos Aires"
        );

        Arbitro arbitro1 = new Arbitro(
                40000001, "Arbitro", "Uno",
                LocalDate.of(1980, 1, 1),
                CategoriaArbitro.NACIONAL, null, "AFA"
        );

        Arbitro arbitro2 = new Arbitro(
                40000002, "Arbitro", "Dos",
                LocalDate.of(1981, 1, 1),
                CategoriaArbitro.NACIONAL, null, "AFA"
        );

        Arbitro arbitro3 = new Arbitro(
                40000003, "Arbitro", "Tres",
                LocalDate.of(1982, 1, 1),
                CategoriaArbitro.NACIONAL, null, "AFA"
        );

        // Programación directa para esta prueba aislada
        boolean programado = partido.programarPartido( torneo,
                LocalDateTime.now().plusDays(2),
                estadio,
                arbitro1,
                arbitro2,
                arbitro3
        );

        System.out.println("Partido programado: " + programado);

        if (!programado) {
            return;
        }

        // 7. Crear las nóminas
        partido.agregarNomina(RolEquipo.LOCAL, partido.getLocal());
        partido.agregarNomina(RolEquipo.VISITANTE, partido.getVisitante());

        // Comprobar que el suspendido no puede ser convocado
        System.out.println("Convocatoria del suspendido: "
                + torneo.convocarTitular(partido, RolEquipo.LOCAL, suspendido));

        // 8. Convocar 11 titulares por equipo
        JugadorPlantel jugadorExpulsado = null;

        int titularesLocales = 0;

        for (JugadorPlantel jp : plantelLocal.getJugadoresPlantel()) {

            if (jp == suspendido) {
                continue;
            }

            if (titularesLocales < 11
                    && torneo.convocarTitular(partido, RolEquipo.LOCAL, jp)) {

                titularesLocales++;

                if (jugadorExpulsado == null) {
                    jugadorExpulsado = jp;
                }
            }
        }

        int titularesVisitantes = 0;

        for (JugadorPlantel jp : plantelVisitante.getJugadoresPlantel()) {

            if (titularesVisitantes < 11
                    && torneo.convocarTitular(partido, RolEquipo.VISITANTE, jp)) {

                titularesVisitantes++;
            }
        }

        System.out.println("Titulares locales: " + titularesLocales);
        System.out.println("Titulares visitantes: " + titularesVisitantes);

        // 9. Iniciar partido y registrar una expulsión
        System.out.println("Partido iniciado: " + partido.iniciar());

        if (partido.getEstado() != EstadoPartido.EN_JUEGO
                || jugadorExpulsado == null) {
            return;
        }

        System.out.println("\n=== REGISTRO DE EVENTOS ===");

// Registrar un gol
        System.out.println("Gol registrado: "
                + controlador.registrarGol(
                partido,
                20,
                jugadorExpulsado.getJugador(),
                TipoGol.A_FAVOR
        ));

// Registrar una expulsión
        System.out.println("Tarjeta roja registrada: "
                + controlador.registrarTarjetaRoja(
                partido,
                35,
                jugadorExpulsado.getJugador()
        ));

        // 10. Finalizar mediante Torneo
        System.out.println("\n=== FINALIZACIÓN ===");

        System.out.println("Partido finalizado: "
                + controlador.finalizarPartido(torneo,partido));

        System.out.println("Jugador anteriormente suspendido: "
                + suspendido.estaSuspendido());

        System.out.println("Jugador expulsado ahora suspendido: "
                + jugadorExpulsado.estaSuspendido());
        System.out.println("\n=== NOTIFICACIONES ===");

        System.out.println("Total de envíos: "
                + servicio.getHistorialEnvios().size());
    }
}
