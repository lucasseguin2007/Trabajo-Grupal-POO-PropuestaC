import java.time.LocalDate;

public class Contrato {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double sueldo;
    private Club club;
    private boolean estado;

    public Contrato(LocalDate fechaInicio, LocalDate fechaFin, double sueldo, Club club) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sueldo = sueldo;
        this.club = club;
        this.estado = true;
    }

    public boolean estaVigente(){
        LocalDate hoy = LocalDate.now();
        if ((!hoy.isBefore(fechaInicio))&&(estado)&&(!hoy.isAfter(fechaFin))){
            return true;
        }
        this.estado = false;
        return false;
    }
    public void rescindir(){
        this.estado = false;
    }
}
