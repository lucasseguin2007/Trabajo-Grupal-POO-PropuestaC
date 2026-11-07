import Enums.ZonaRemate;

public class Remate extends Evento {
    private ZonaRemate zona;

    public Remate(int minuto, Jugador jugador, ZonaRemate zona) {
        super(minuto, jugador);
        if (zona == null) {
            throw new IllegalArgumentException("La zona del remate no puede ser nula");
        }
        this.zona = zona;
    }

    public ZonaRemate getZona() {
        return zona;
    }

    @Override
    public String obtenerDescripcion() {
        return "Zona del Remate: " + zona +
                " | minuto: " + getMinuto() +
                " | jugador: " + getJugador().getNombre() +
                " " + getJugador().getApellido();
    }

    public double calcularXG() {
        switch (zona) {
            case AREA_CHICA:
                return 0.40;
            case AREA_GRANDE:
                return 0.15;
            case FUERA_DEL_AREA:
                return 0.04;
            case PENAL:
                return 0.76;
            default:
                throw new IllegalStateException("Zona de Remate no registrada.");
        }
    }
}
