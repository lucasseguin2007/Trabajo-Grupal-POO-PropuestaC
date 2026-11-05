public class TarjetaRoja extends Evento{
    private boolean directa;

    public TarjetaRoja(int minuto, Jugador jugador, boolean directa) {
        super(minuto, jugador);
        this.directa = directa;
    }

    public boolean isDirecta() {
        return directa;
    }

    @Override
    public String obtenerDescripcion() {
        String tipo;
        if (directa) {
            tipo= "Directa";
        } else {
            tipo= "Doble Amarilla";
        }
        return "Tarjeta Roja "
                + " | minuto: " + getMinuto()
                + " | jugador: " + getJugador().getNombre()
                + " " + getJugador().getApellido()
                + " | tipo: " + tipo;
    }
}
