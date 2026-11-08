package partidos;

import clubes.Direccion;
import enums.TipoCesped;

import java.io.Serializable;

public class Estadio implements Serializable {
    private String nombre;
    private int capacidad;
    private String climaHabitual;
    private TipoCesped tipoCesped;
    private Direccion direccion;

    public Estadio(String nombre, int capacidad, String climaHabitual, TipoCesped tipoCesped, String calle, int numero, String ciudad, String provincia) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.climaHabitual = climaHabitual;
        this.tipoCesped = tipoCesped;
        this.direccion = new Direccion(calle,numero,ciudad,provincia);
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
