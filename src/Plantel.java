public class Plantel {
    private JugadorPlantel jugadoresPlantel[];

    public Plantel() {
        this.jugadoresPlantel = new JugadorPlantel[30];
    }
    public void agregarJugador(int numero, Jugador jugador ){
        for (int i = 0; i < jugadoresPlantel.length; i++) {
            JugadorPlantel jp = new JugadorPlantel(numero, jugador);
            jugadoresPlantel[i] = jp;
        }
    }
}
