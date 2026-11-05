import Enums.TipoGol;

public class Gol extends Evento {
    private TipoGol tipo;

    public Gol(int minuto, Jugador jugador, TipoGol tipo) {
        super(minuto, jugador);
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de gol no puede nulo");
        }
        this.tipo = tipo;
    }

    public TipoGol getTipo() {
        return tipo;
    }

    @Override
    public String obtenerDescripcion() {
        return "Gol del Jugador: " + getJugador().getNombre() +
                " " + getJugador().getApellido()
                + " y el tipo de gol: " + tipo ;
    }

}
