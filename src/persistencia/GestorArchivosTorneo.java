package persistencia;
import torneos.Torneo;
import torneos.GestorTorneos;
import java.io.*;

public class GestorArchivosTorneo {
    public void guardarTorneos(GestorTorneos gestor, String ruta) throws IOException {
        if (gestor == null) {
            throw new IllegalArgumentException("El gestor no puede ser nulo");
        }
        if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta no puede estar vacía");
        }
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta))) {
            salida.writeObject(gestor);
        }
    }

    public GestorTorneos cargarTorneos(String ruta) throws IOException, ClassNotFoundException {
        if (ruta == null || ruta.isBlank()) {
            throw new IllegalArgumentException("La ruta no puede estar vacía");
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta))) {
            return (GestorTorneos) entrada.readObject();
        }
    }
}
