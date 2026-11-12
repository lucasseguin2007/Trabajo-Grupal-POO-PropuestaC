package pruebas;

import clubes.Club;
import clubes.GestorContratos;
import enums.Posicion;
import enums.RolEquipo;
import enums.TipoGol;
import enums.CategoriaArbitro;
import enums.TipoCesped;
import personas.Jugador;
import personas.Arbitro;
import partidos.Estadio;
import partidos.Partido;
import reportes.GestorReportes;
import reportes.PosicionTorneo;
import torneos.InscripcionTorneo;
import torneos.Torneo;
import torneos.JugadorPlantel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainPruebaTablaPosiciones {
    public static void main(String[] args) {

        LocalDate hoy = LocalDate.now();
        Torneo torneo = new Torneo("Liga de prueba", hoy.plusDays(1), hoy.plusDays(60));
        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Club river = new Club("River", LocalDate.of(1901, 5, 25), null);
        Club racing = new Club("San Lorenzo", LocalDate.of(1903, 3, 25), null);
        Club independiente = new Club("Independiente", LocalDate.of(1905, 1, 1), null);

        torneo.inscribirClub(boca);
        torneo.inscribirClub(river);
        torneo.inscribirClub(racing);
        torneo.inscribirClub(independiente);
        GestorContratos gestorContratos = new GestorContratos();
        cargarPlantel(torneo, boca, gestorContratos, 1000);
        cargarPlantel(torneo, river, gestorContratos, 2000);
        cargarPlantel(torneo, racing, gestorContratos, 3000);
        cargarPlantel(torneo, independiente, gestorContratos, 4000);

        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            System.out.println("Inscripción confirmada: " + inscripcion.confirmarInscripcion());
        }
        List<Partido> partidos = torneo.generarFixture();
        Estadio estadio = new Estadio("Estadio de prueba", 50000, "Templado", TipoCesped.values()[0], "Av. Principal", 100, "Buenos Aires", "Buenos Aires");
        Arbitro arbitro1 = new Arbitro(5001, "Carlos", "Lopez", LocalDate.of(1980, 1, 1), CategoriaArbitro.values()[0], null, "AFA");
        Arbitro arbitro2 = new Arbitro(5002, "Martin", "Gomez", LocalDate.of(1982, 2, 2), CategoriaArbitro.values()[0], null, "AFA");
        Arbitro arbitro3 = new Arbitro(5003, "Juan", "Perez", LocalDate.of(1985, 3, 3), CategoriaArbitro.values()[0], null, "AFA");

        int dia = 1;
        for (Partido partido : partidos) {
            LocalDateTime fecha = hoy.plusDays(dia).atTime(15, 0);
            boolean programado = torneo.programarPartido(partido, fecha, estadio, arbitro1, arbitro2, arbitro3);
            if (!programado) {
                System.out.println("No se pudo programar un partido.");
                return;
            }

            partido.agregarNomina(RolEquipo.LOCAL, partido.getLocal());
            partido.agregarNomina(RolEquipo.VISITANTE, partido.getVisitante());
            convocarTitulares(torneo, partido, partido.getLocal(), RolEquipo.LOCAL);
            convocarTitulares(torneo, partido, partido.getVisitante(), RolEquipo.VISITANTE);
            dia += 3;
        }
        if (!torneo.iniciarTorneo()) {
            System.out.println("No se pudo iniciar el torneo.");
            return;
        }
        int numeroPartido = 1;
        for (Partido partido : partidos) {
            if (!partido.iniciar()) {
                System.out.println("No se pudo iniciar el partido " + numeroPartido);
                return;
            }

            int golesLocal = numeroPartido % 3;
            int golesVisitante = (numeroPartido + 1) % 3;
            Jugador jugadorLocal = partido.getNominas().get(0).getJugadoresTitulares().get(0);
            Jugador jugadorVisitante = partido.getNominas().get(1).getJugadoresTitulares().get(0);
            for (int i = 0; i < golesLocal; i++) {
                partido.registrarGol(10 + i, jugadorLocal, TipoGol.A_FAVOR);
            }
            for (int i = 0; i < golesVisitante; i++) {
                partido.registrarGol(20 + i, jugadorVisitante, TipoGol.A_FAVOR);
            }
            torneo.finalizarPartido(partido);
            numeroPartido++;
        }

        GestorReportes gestorReportes = new GestorReportes();
        List<PosicionTorneo> tabla = gestorReportes.generarTablaPosiciones(torneo);
        System.out.println("\nTabla de posiciones:");
        for (int i = 0; i < tabla.size(); i++) {
            PosicionTorneo posicion = tabla.get(i);
            System.out.println((i + 1) + ". " + posicion.getClub().getNombre());
            System.out.println("Partidos jugados: " + posicion.getPartidosJugados());
            System.out.println("Ganados: " + posicion.getPartidosGanados());
            System.out.println("Empatados: " + posicion.getPartidosEmpatados());
            System.out.println("Perdidos: " + posicion.getPartidosPerdidos());
            System.out.println("Goles a favor: " + posicion.getGolesAFavor());
            System.out.println("Goles en contra: " + posicion.getGolesEnContra());
            System.out.println("Diferencia de gol: " + posicion.calcularDiferenciaGol());
            System.out.println("Puntos: " + posicion.getPuntos());
            System.out.println();
        }
    }

    private static void cargarPlantel(Torneo torneo, Club club, GestorContratos gestor, int dniInicial) {
        InscripcionTorneo inscripcion = obtenerInscripcion(torneo, club);
        for (int i = 0; i < 18; i++) {
            Jugador jugador = new Jugador(dniInicial + i, "Jugador" + i, club.getNombre(), LocalDate.of(2000, 1, 1), Posicion.values()[0], 100000);
            gestor.registrarAlta(jugador, club, LocalDate.now().minusDays(1), LocalDate.now().plusYears(2), 100000);
            inscripcion.agregarJugador(i + 1, jugador);
        }
    }

    private static InscripcionTorneo obtenerInscripcion(Torneo torneo, Club club) {
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            if (inscripcion.getClub().equals(club)) {
                return inscripcion;
            }
        }
        return null;
    }

    private static void convocarTitulares(Torneo torneo, Partido partido, Club club, RolEquipo rol) {
        InscripcionTorneo inscripcion = obtenerInscripcion(torneo, club);
        List<JugadorPlantel> jugadores = inscripcion.getPlantel().getJugadoresPlantel();
        for (int i = 0; i < 11; i++) {
            if (!torneo.convocarTitular(partido, rol, jugadores.get(i))) {
                throw new IllegalStateException("No se pudo convocar un titular.");
            }
        }
    }
}
