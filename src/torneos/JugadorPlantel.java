package torneos;

import personas.Jugador;

public class JugadorPlantel {
    private int numeroCamiseta;
    private Jugador jugador;
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
    }
    //Getters

    public Jugador getJugador() {
        return jugador;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }
}
