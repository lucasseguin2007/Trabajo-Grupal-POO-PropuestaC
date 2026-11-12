package pruebas;

import clubes.Club;
import persistencia.GestorArchivoClubes;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainPruebaPersistenciaClubes {
    public static void main(String[] args) {

        String ruta = "clubes-prueba.txt";
        List<Club> clubes = new ArrayList<>();
        clubes.add(new Club("Boca", LocalDate.of(1905, 4, 3), null));
        clubes.add(new Club("River", LocalDate.of(1901, 5, 25), null));
        clubes.add(new Club("Racing", LocalDate.of(1903, 3, 25), null));
        clubes.add(new Club("Independiente", LocalDate.of(1905, 1, 1), null));
        GestorArchivoClubes archivos = new GestorArchivoClubes();

        try {
            archivos.guardarClubes(clubes, ruta);
            System.out.println("Clubes guardados: " + clubes.size());
            List<Club> clubesCargados = archivos.cargarClubes(ruta);
            System.out.println("Clubes recuperados: " + clubesCargados.size());
            for (Club club : clubesCargados) {
                System.out.println(club.getNombre());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar o cargar los clubes: " + e.getMessage());
        }
    }
}

