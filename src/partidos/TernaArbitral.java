package partidos;

import personas.Arbitro;

public class TernaArbitral {
    private Arbitro arbitros[];

    public TernaArbitral() {
        this.arbitros = new Arbitro[3];
    }

    public Arbitro[] getArbitros() {
        return arbitros.clone();
    }
    public boolean agregarArbitro(Arbitro arbitro) {
        if (arbitro == null) {
            return false;
        }
        for (Arbitro a : arbitros) {
            if (a == arbitro) {
                return false;
            }
        }
        for (int i = 0; i < arbitros.length; i++) {
            if (arbitros[i] == null) {
                arbitros[i] = arbitro;
                return true;
            }
        }
        return false;
    }

    public boolean estaCompleta() {
        for (Arbitro arbitro : arbitros) {
            if (arbitro == null) {
                return false;
            }
        }
        return true;
    }
}