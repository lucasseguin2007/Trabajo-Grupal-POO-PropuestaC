package Aplicacion;

import clubes.Club;
import persistencia.GestorArchivosTorneo;
import torneos.GestorTorneos;
import torneos.Torneo;

import java.io.IOException;
import java.time.LocalDate;

public class MainPruebaPersistencia {

    public static void main(String[] args) {

        String ruta = "torneos-prueba.dat";

        try {

            // 1. Crear gestor
            GestorTorneos gestor = new GestorTorneos();

            // 2. Crear torneo
            Torneo torneo = new Torneo(
                    "Liga Argentina 2026",
                    LocalDate.of(2026, 1, 1),
                    LocalDate.of(2026, 12, 31)
            );

            // 3. Crear clubes
            Club boca = new Club(
                    "Boca",
                    LocalDate.of(1905, 4, 3),
                    null
            );

            Club river = new Club(
                    "River",
                    LocalDate.of(1901, 5, 25),
                    null
            );

            // 4. Inscribir clubes
            torneo.inscribirClub(boca);
            torneo.inscribirClub(river);

            // 5. Registrar torneo actual
            System.out.println("Torneo registrado: "
                    + gestor.iniciarNuevoTorneo(torneo));

            // 6. Guardar
            GestorArchivosTorneo archivos = new GestorArchivosTorneo();

            archivos.guardarTorneos(gestor, ruta);

            System.out.println("Torneos guardados correctamente.");

            // 7. Recuperar desde el archivo
            GestorTorneos gestorCargado = archivos.cargarTorneos(ruta);

            System.out.println("\n=== DATOS RECUPERADOS ===");

            Torneo torneoCargado = gestorCargado.getTorneoActual();

            if (torneoCargado != null) {

                System.out.println("Estado del torneo: "
                        + torneoCargado.getEstado());

                System.out.println("Clubes inscriptos: "
                        + torneoCargado.getInscriptos().size());

                torneoCargado.getInscriptos().forEach(
                        inscripcion -> System.out.println(
                                "- " + inscripcion.getClub().getNombre()
                        )
                );
            }

            System.out.println("Torneos en historial: "
                    + gestorCargado.getHistorialTorneos().size());

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar o cargar: " + e.getMessage()
            );

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "No se pudo reconstruir el objeto: " + e.getMessage()
            );
        }
    }
}
