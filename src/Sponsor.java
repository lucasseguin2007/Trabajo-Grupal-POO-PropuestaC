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
}