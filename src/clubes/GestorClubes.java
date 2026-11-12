
package clubes;

import persistencia.GestorArchivoClubes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorClubes {

    private List<Club> clubes;
    private GestorArchivoClubes gestorArchivos;
    private String ruta = "clubes.txt";

    public GestorClubes() {

        this.gestorArchivos = new GestorArchivoClubes();

        try {
            this.clubes = gestorArchivos.cargarClubes(ruta);
        } catch (IOException e) {
            this.clubes = new ArrayList<>();
        }
    }

    public boolean guardarCambios() {
        try {
            gestorArchivos.guardarClubes(clubes,ruta);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean agregarClub(Club club) {

        if (club == null) {
            return false;
        }

        for (Club existente : clubes) {
            if (existente.getNombre().equalsIgnoreCase(club.getNombre())) {
                return false;
            }
        }

        clubes.add(club);

        try {
            gestorArchivos.guardarClubes(clubes, ruta);
        } catch (IOException e) {
            clubes.remove(club);
            return false;
        }

        return true;
    }

    public Club buscarClub(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            return null;
        }

        for (Club club : clubes) {
            if (club.getNombre().equalsIgnoreCase(nombre)) {
                return club;
            }
        }

        return null;
    }

    public List<Club> getClubes() {
        return new ArrayList<>(clubes);
    }
}
