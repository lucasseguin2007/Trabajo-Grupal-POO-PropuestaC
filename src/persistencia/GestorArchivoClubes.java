
package persistencia;

import clubes.Club;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivoClubes {

    public void guardarClubes(List<Club> clubes, String ruta) throws IOException {
        if (clubes == null) {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }

        if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta no puede estar vacía");
        }

        try (BufferedWriter salida = new BufferedWriter(new FileWriter(ruta))) {

            for (Club club : clubes) {
                salida.write(club.getNombre() + "|" + club.getFechaFundacion());
                salida.newLine();
            }
        }
    }

    public List<Club> cargarClubes(String ruta) throws IOException {

        if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta no puede estar vacía");
        }

        List<Club> clubes = new ArrayList<>();

        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return clubes;
        }

        try (BufferedReader entrada = new BufferedReader(new FileReader(ruta))) {

            String linea;

            while ((linea = entrada.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split("\\|");

                if (datos.length != 2) {
                    continue;
                }

                String nombre = datos[0];
                LocalDate fechaFundacion = LocalDate.parse(datos[1]);

                Club club = new Club(nombre, fechaFundacion, null);

                clubes.add(club);
            }
        }

        return clubes;
    }
}
