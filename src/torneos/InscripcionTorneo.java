package torneos;

import clubes.Club;

import java.time.LocalDate;

public class InscripcionTorneo {
    private LocalDate fechaInscripcion;
    private Plantel p;
    private Club c;

    public InscripcionTorneo(LocalDate fechaInscripcion, Club c) {
        this.fechaInscripcion = fechaInscripcion;
        this.p = new Plantel();
        this.c = c;
    }

    public Club getC() {
        return c;
    }
}
