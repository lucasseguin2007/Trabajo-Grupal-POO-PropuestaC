public class TarjetaAmarilla extends Evento {
    public TarjetaAmarilla(int minuto, Jugador jugador) {
        super(minuto, jugador);
    }

    @Override
    public String obtenerDescripcion() {
        return "Tarjeta Amarilla"
                + " | minuto: " + getMinuto()
                + " | Jugador: " + getJugador().getNombre() +
                " " + getJugador().getApellido();
    }
}
