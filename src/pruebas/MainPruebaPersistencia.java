package pruebas;

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
            GestorTorneos gestor = new GestorTorneos();
            Torneo torneo = new Torneo("Liga Argentina 2026", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31));
            Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
            Club river = new Club("River", LocalDate.of(1901, 5, 25), null);

            torneo.inscribirClub(boca);
            torneo.inscribirClub(river);

            System.out.println("Torneo registrado: " + gestor.iniciarNuevoTorneo(torneo));
            GestorArchivosTorneo archivos = new GestorArchivosTorneo();
            archivos.guardarTorneos(gestor, ruta);
            System.out.println("Torneos guardados correctamente.");
            GestorTorneos gestorCargado = archivos.cargarTorneos(ruta);
            System.out.println("\nDatos recuperados:");
            Torneo torneoCargado = gestorCargado.getTorneoActual();

            if (torneoCargado != null) {
                System.out.println("Estado del torneo: " + torneoCargado.getEstado());
                System.out.println("Clubes inscriptos: " + torneoCargado.getInscriptos().size());
                torneoCargado.getInscriptos().forEach(inscripcion -> System.out.println("- " + inscripcion.getClub().getNombre()));
            }

            System.out.println("Torneos en historial: " + gestorCargado.getHistorialTorneos().size());
        } catch (IOException e) {
            System.out.println("Error al guardar o cargar: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo reconstruir el objeto: " + e.getMessage());
        }
    }
}
