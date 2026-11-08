package Aplicacion;

import clubes.Club;
import clubes.GestorContratos;
import enums.Posicion;
import personas.Jugador;
import partidos.Partido;
import torneos.InscripcionTorneo;
import torneos.Torneo;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Torneo torneo = new Torneo(
                "Liga Argentina",
                LocalDate.now().plusDays(30),
                LocalDate.now().plusMonths(6)
        );

        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Club river = new Club("River", LocalDate.of(1901, 5, 25), null);
        Club racing = new Club("Racing", LocalDate.of(1903, 3, 25), null);
        Club independiente = new Club("Independiente", LocalDate.of(1905, 1, 1), null);
        Club sanLorenzo = new Club("San Lorenzo", LocalDate.of(1908, 4, 1), null);

        torneo.inscribirClub(sanLorenzo);
        torneo.inscribirClub(boca);
        torneo.inscribirClub(river);
        torneo.inscribirClub(racing);
        torneo.inscribirClub(independiente);

        GestorContratos gestor = new GestorContratos();

        int dni = 30000000;

        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {

            for (int i = 1; i <= 18; i++) {

                Jugador jugador = new Jugador(
                        dni++,
                        "Jugador" + i,
                        "Prueba",
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
                            + ": " + inscripcion.confirmarInscripcion()
            );
        }

        List<Partido> fixture = torneo.generarFixture();

        System.out.println("\nCantidad de partidos: " + fixture.size());

        for (Partido partido : fixture) {
            System.out.println(
                    "Jornada " + partido.getJornada()
                            + ": " + partido.getLocal().getNombre()
                            + " vs. " + partido.getVisitante().getNombre()
            );
        }
    }
}
