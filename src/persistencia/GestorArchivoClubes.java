
package persistencia;

import clubes.Club;
import enums.EspecialidadEntrenador;
import personas.Entrenador;

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
               String linea = club.getNombre() + "|" + club.getFechaFundacion();
                Entrenador entrenador = club.getEntrenador();
                if (entrenador != null) {
                    linea += "|" + entrenador.getDni()
                            + "|" + entrenador.getNombre()
                            + "|" + entrenador.getApellido()
                            + "|" + entrenador.getFechaNacimiento()
                            + "|" + entrenador.getEspecialidad()
                            + "|" + entrenador.getLicencia();
                }
                salida.write(linea);
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
                String[] datos = linea.split("\\|",-1);
                if (datos.length != 2 && datos.length != 8) {
                    throw new IOException("Formato de club invalido: " + linea);
                }
                try {
                    String nombreClub = datos[0];
                    LocalDate fechaFundacion = LocalDate.parse(datos[1]);
                    Club club = new Club(nombreClub, fechaFundacion, null);
                    if (datos.length == 8) {
                        int dni = Integer.parseInt(datos[2]);
                        String nombre = datos[3];
                        String apellido = datos[4];
                        LocalDate nacimiento = LocalDate.parse(datos[5]);
                        EspecialidadEntrenador especialidad = EspecialidadEntrenador.valueOf(datos[6]);
                        String licencia = datos[7];
                        Entrenador entrenador = new Entrenador(dni,nombre,apellido,nacimiento,especialidad,licencia);
                        club.asignarEntrenador(entrenador);
                    }
                    clubes.add(club);
                } catch (IllegalArgumentException e) {
                    throw new IOException("Datos de club invalidos: " + linea,e);
                }

            }
        }
        return clubes;
    }
}
