import java.time.LocalDate;

public class InscripcionTorneo {
    private LocalDate fechaInscripcion;
    private Plantel p;

    public InscripcionTorneo(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
        this.p = new Plantel();
    }
}
