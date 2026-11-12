package pruebas;

import clubes.Club;
import clubes.GestorContratos;
import enums.*;
import notificaciones.CanalEnvio;
import notificaciones.ServicioNotificaciones;
import partidos.Estadio;
import partidos.Partido;
import personas.Arbitro;
import personas.Jugador;
import torneos.*;
import controladores.ControladorPartidos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainPruebaPartidos {
    public static void main(String[] args) {

        Torneo torneo = new Torneo("Liga de prueba", LocalDate.now().plusDays(1), LocalDate.now().plusMonths(3));
        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Club river = new Club("River", LocalDate.of(1901, 5, 25), null);
        torneo.inscribirClub(boca);
        torneo.inscribirClub(river);
        GestorContratos gestor = new GestorContratos();

        int dni = 30000000;
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            for (int i = 1; i <= 18; i++) {
                Jugador jugador = new Jugador(dni++, "Jugador" + i, inscripcion.getClub().getNombre(), LocalDate.of(2000, 1, 1), Posicion.DEFENSOR, 100000);
                gestor.registrarAlta(jugador, inscripcion.getClub(), LocalDate.now().minusDays(1), LocalDate.now().plusYears(1), 100000);
                inscripcion.agregarJugador(i, jugador);
            }
            System.out.println(inscripcion.getClub().getNombre() + " confirmado: " + inscripcion.confirmarInscripcion());
        }

        torneo.generarFixture();
        Partido partido = torneo.getPartidos().get(0);
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
        Estadio estadio = new Estadio("Estadio de prueba", 50000, "Templado", TipoCesped.NATURAL, "Calle Principal", 100, "Buenos Aires", "Buenos Aires");
        Arbitro arbitro1 = new Arbitro(40000001, "Arbitro", "Uno", LocalDate.of(1980, 1, 1), CategoriaArbitro.NACIONAL, null, "AFA");
        Arbitro arbitro2 = new Arbitro(40000002, "Arbitro", "Dos", LocalDate.of(1981, 1, 1), CategoriaArbitro.NACIONAL, null, "AFA");
        Arbitro arbitro3 = new Arbitro(40000003, "Arbitro", "Tres", LocalDate.of(1982, 1, 1), CategoriaArbitro.NACIONAL, null, "AFA");
        boolean programado = partido.programarPartido(torneo, LocalDateTime.now().plusDays(2), estadio, arbitro1, arbitro2, arbitro3);
        System.out.println("Partido programado: " + programado);
        if (!programado) {
            return;
        }
        partido.agregarNomina(RolEquipo.LOCAL, partido.getLocal());
        partido.agregarNomina(RolEquipo.VISITANTE, partido.getVisitante());
        JugadorPlantel goleador = null;
        int titularesLocales = 0;
        for (JugadorPlantel jp : plantelLocal.getJugadoresPlantel()) {
            if (titularesLocales < 11 && torneo.convocarTitular(partido, RolEquipo.LOCAL, jp)) {
                titularesLocales++;
                if (goleador == null) {
                    goleador = jp;
                }
            }
        }
        int titularesVisitantes = 0;
        for (JugadorPlantel jp : plantelVisitante.getJugadoresPlantel()) {
            if (titularesVisitantes < 11 && torneo.convocarTitular(partido, RolEquipo.VISITANTE, jp)) {
                titularesVisitantes++;
            }
        }
        System.out.println("Titulares locales: " + titularesLocales);
        System.out.println("Titulares visitantes: " + titularesVisitantes);
        if (titularesLocales < 11 || titularesVisitantes < 11 || goleador == null) {
            System.out.println("No se pudieron completar las nóminas.");
            return;
        }
        List<CanalEnvio> canales = new ArrayList<>();
        ServicioNotificaciones servicio = new ServicioNotificaciones(canales);
        ControladorPartidos controlador = new ControladorPartidos(servicio);
        System.out.println("Partido iniciado: " + partido.iniciar());
        if (partido.getEstado() != EstadoPartido.EN_JUEGO) {
            return;
        }
        System.out.println("Gol registrado: " + controlador.registrarGol(partido, 20, goleador.getJugador(), TipoGol.A_FAVOR));
        System.out.println("Tarjeta roja registrada: " + controlador.registrarTarjetaRoja(partido, 35, goleador.getJugador()));
        System.out.println("Partido finalizado: " + controlador.finalizarPartido(torneo, partido));
        System.out.println("Estado final: " + partido.getEstado());
    }
}

