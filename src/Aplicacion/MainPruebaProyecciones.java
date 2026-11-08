package Aplicacion;
import clubes.Club;
import clubes.GestorContratos;
import enums.Posicion;
import partidos.Partido;
import personas.Jugador;
import reportes.ProyeccionClasificacion;
import reportes.ProyeccionPorPromedio;
import reportes.ProyeccionPorSimulacionXG;
import torneos.InscripcionTorneo;
import torneos.Torneo;

import java.time.LocalDate;

public class MainPruebaProyecciones {
    public static void main(String[] args) {

        // 1. Crear torneo
        Torneo torneo = new Torneo(
                "Liga Argentina",
                LocalDate.now().plusDays(30),
                LocalDate.now().plusMonths(6)
        );

        // 2. Crear clubes
        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Club river = new Club("River", LocalDate.of(1901, 5, 25), null);
        Club racing = new Club("Racing", LocalDate.of(1903, 3, 25), null);
        Club independiente = new Club("Independiente", LocalDate.of(1905, 1, 1), null);

        torneo.inscribirClub(boca);
        torneo.inscribirClub(river);
        torneo.inscribirClub(racing);
        torneo.inscribirClub(independiente);

        // 3. Incorporar jugadores
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
                            + " inscripto: "
                            + inscripcion.confirmarInscripcion()
            );
        }

        // 4. Generar fixture
        System.out.println("\nPartidos generados: "
                + torneo.generarFixture().size());
        // 5. Probar ambas estrategias de proyección
        ProyeccionClasificacion proyeccion;

        proyeccion = new ProyeccionPorPromedio(8);

        double probabilidadPromedio =
                proyeccion.calcularProbabilidad(torneo, boca);

        proyeccion = new ProyeccionPorSimulacionXG(8);

        double probabilidadXG =
                proyeccion.calcularProbabilidad(torneo, boca);

        // 6. Mostrar resultados
        System.out.println("\n=== PROYECCIONES DE BOCA ===");

        System.out.println("Proyección por promedio: "
                + probabilidadPromedio * 100 + "%");

        System.out.printf("Proyección por simulación xG: %.2f%%%n", probabilidadXG * 100);

    }
}

