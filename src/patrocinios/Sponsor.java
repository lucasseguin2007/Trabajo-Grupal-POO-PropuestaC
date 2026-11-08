package patrocinios;

import java.util.ArrayList;
import java.util.List;

public class Sponsor {
    private String nombre;
    private String marca;
    private String logo;
    private List<Patrocinio> patrocinios;

    public Sponsor(String nombre, String marca, String logo) {
        this.nombre = nombre;
        this.marca = marca;
        this.logo = logo;
        this.patrocinios = new ArrayList<>();
    }
    public List<Patrocinio> getPatrocinios() {
        return new ArrayList<>(patrocinios);
    }

    public boolean agregarPatrocinio(Patrocinio patrocinio) {
        if (patrocinio == null){
            return false;
        }
        if (patrocinio.getSponsor() != this) {
            return false;
        }
        if (patrocinios.contains(patrocinio)) {
            return false;
        }
        patrocinios.add(patrocinio);
        return true;
    }
}