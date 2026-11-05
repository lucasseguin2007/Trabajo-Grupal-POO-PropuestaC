public class Falta extends Evento{
    public Falta(int minuto, Jugador jugador) {
        super(minuto, jugador);
    }

    @Override
    public String obtenerDescripcion() {
        return "Falta de: " + getJugador().getNombre()
                + " " + getJugador().getApellido()
                + " | minuto: " + getMinuto();
    }
}
