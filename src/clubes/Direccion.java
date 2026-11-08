package clubes;

import java.io.Serializable;

public class Direccion implements Serializable {
    private String calle;
    private int numero;
    private String ciudad;
    private String provincia;

    public Direccion(String calle, int numero, String ciudad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return
                "calle='" + calle + '\'' +
                ", numero=" + numero +
                ", ciudad='" + ciudad + '\'' +
                ", provincia='" + provincia;
    }
}
