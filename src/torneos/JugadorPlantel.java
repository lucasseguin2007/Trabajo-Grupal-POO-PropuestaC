package torneos;

import personas.Jugador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JugadorPlantel implements Serializable {
    private int numeroCamiseta;
    private Jugador jugador;
    private List<Sancion> sanciones;
    //Metodo constructor
    public JugadorPlantel(int numeroCamiseta, Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        if (numeroCamiseta < 1 || numeroCamiseta > 99) {
            throw new IllegalArgumentException("El número de camiseta debe estar entre 1 y 99");
        }
        this.numeroCamiseta = numeroCamiseta;
        this.jugador = jugador;
        this.sanciones = new ArrayList<>();
    }
    //Getters

    public Jugador getJugador() {
        return jugador;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }
    //Metodos
    public boolean agregarSancion(Sancion sancion) {
        if (sancion == null || sancion.getJugador() != this ) {
            return false;
        }
        sanciones.add(sancion);
        return true;
    }

    public boolean estaSuspendido() {
        for (Sancion sancion : sanciones) {
            if (sancion.estaVigente()) {
                return true;
            }
        }
        return false;
    }

    public void cumplirFechaSuspension() {
        for (Sancion sancion : sanciones) {
            if (sancion.estaVigente()) {
                sancion.cumplirFecha();
            }
        }
    }


}
