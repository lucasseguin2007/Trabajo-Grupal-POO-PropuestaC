package eventos;

import personas.Jugador;

public class Cambio extends Evento {
    private Jugador jugadorEntra;

    public Cambio(int minuto, Jugador jugadorSale, Jugador jugadorEntra) {
        super(minuto, jugadorSale);
        if (jugadorEntra == null || jugadorEntra.equals(jugadorSale)){
            throw new IllegalArgumentException("Los jugadores deben existir y ser distintos.");
        }
        this.jugadorEntra = jugadorEntra;
    }

    public Jugador getJugadorEntra() {
        return jugadorEntra;
    }

    @Override
    public String obtenerDescripcion() {
        return "Eventos.Cambio: "
                + " | Personas.Jugador que salio: " + getJugador().getNombre()
                + " " + getJugador().getApellido()
                + " | Personas.Jugador que entra: " + jugadorEntra.getNombre()
                + " " + jugadorEntra.getApellido()
                + " | minuto: " + getMinuto();
    }
}
