public class Plantel {
    private JugadorPlantel jugadoresPlantel[];

    public Plantel() {
        this.jugadoresPlantel = new JugadorPlantel[30];
    }
    public void agregarJugadores(int numero){
        for (int i = 0; i < jugadoresPlantel.length; i++) {
            JugadorPlantel jp = new JugadorPlantel(numero);
            jugadoresPlantel[i] = jp;
        }
    }
}
