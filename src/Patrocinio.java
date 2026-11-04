import java.time.LocalDate;

public class Patrocinio {
    private Sponsor sponsor;
    private double monto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean estado;

    public Patrocinio(Sponsor sponsor, double monto, LocalDate fechaInicio, LocalDate fechaFin, Boolean estado) {
        this.sponsor = sponsor;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public boolean estaVigente(){
        return fechaFin.isAfter(LocalDate.now());
    }

}
