package eventos;

import personas.Jugador;

import java.io.Serializable;

public abstract class Evento implements Serializable {
    private int minuto;
    private Jugador jugador;

    public Evento(int minuto, Jugador jugador) {
        if (minuto < 0 || minuto > 120) {
            throw new IllegalArgumentException("El minuto debe estar entre 0 y 120");
        }
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        this.minuto = minuto;
        this.jugador = jugador;
    }

    public int getMinuto() {
        return minuto;
    }
    public Jugador getJugador() {
        return jugador;
    }

    public abstract String obtenerDescripcion();
}
