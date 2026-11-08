package eventos;

import personas.Jugador;

public class Falta extends Evento {
    public Falta(int minuto, Jugador jugador) {
        super(minuto, jugador);
    }

    @Override
    public String obtenerDescripcion() {
        return "Eventos.Falta de: " + getJugador().getNombre()
                + " " + getJugador().getApellido()
                + " | minuto: " + getMinuto();
    }
}
