package partidos;

import personas.Arbitro;

public class TernaArbitral {
    private Arbitro arbitros[];

    public TernaArbitral() {
        this.arbitros = new Arbitro[3];
    }
    public void agregarArbitro(Arbitro arbitro) {
        if (arbitros.length < 3){
        for (int i = 0; i < arbitros.length; i++) {
            if (arbitros[i] == null) {
                arbitros[i] = arbitro;
                return;
            }
        }
        }
        System.out.println("Terna de arbitros completa");
    }
}