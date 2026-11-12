package pruebas;
import clubes.Club;
import clubes.GestorContratos;
import enums.Posicion;
import personas.Jugador;
import torneos.InscripcionTorneo;
import torneos.Torneo;
import java.time.LocalDate;

public class MainPruebaFixture {
    public static void main(String[] args) {

        Torneo torneo = new Torneo("Liga de prueba", LocalDate.now().plusDays(1), LocalDate.now().plusMonths(3));
        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Club river = new Club("River", LocalDate.of(1901, 5, 25), null);
        Club racing = new Club("Racing", LocalDate.of(1903, 3, 25), null);
        Club independiente = new Club("Independiente", LocalDate.of(1905, 1, 1), null);

        torneo.inscribirClub(boca);
        torneo.inscribirClub(river);
        torneo.inscribirClub(racing);
        torneo.inscribirClub(independiente);
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
        System.out.println("\nPartidos generados: " + torneo.generarFixture().size());
        for (int i = 0; i < torneo.getPartidos().size(); i++) {
            System.out.println("Partido " + (i + 1) + ": " + torneo.getPartidos().get(i).getLocal().getNombre() + " vs " + torneo.getPartidos().get(i).getVisitante().getNombre());
        }
    }
}

