package clubes;

import java.time.LocalDate;

public class Contrato {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double sueldo;
    private Club club;
    private boolean estado;
    private LocalDate fechaRescision;

    //Metodo Constructor
    public Contrato(LocalDate fechaInicio, LocalDate fechaFin, double sueldo, Club club) {
        if (fechaInicio == null || fechaFin == null || club == null) {
            throw new IllegalArgumentException("Los datos no pueden ser null");
        }
        if (fechaFin.isBefore(fechaInicio)){
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior al inicio");
        }
        if (sueldo < 0) {
            throw new IllegalArgumentException("El sueldo no puede ser negativo");
        }
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sueldo = sueldo;
        this.club = club;
        this.estado = true;
        this.fechaRescision = null;
    }

    //Getters and Setters
    public Club getClub() {
        return club;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean isEstado() {
        return estado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaRescision() {
        return fechaRescision;
    }

    //Metodos
    public boolean rescindir() {
        if (!estado || !estaVigente()) {
            return false;
        }
        estado = false;
        fechaRescision = LocalDate.now();
        return true;
    }

    public boolean estaVigente(){
        LocalDate hoy = LocalDate.now();
        if ((!hoy.isBefore(fechaInicio))&&(estado)&&(!hoy.isAfter(fechaFin))){
            return true;
        }
        return false;
    }

    public boolean seSuperpone(Contrato otro){
        if (otro == null) {
            return false;
        }
        LocalDate finEfectivo;
        if (!estado) {
            finEfectivo = fechaRescision;
        } else {
            finEfectivo = fechaFin;
        }
        LocalDate finOtro;
        if (!otro.estado) {
            finOtro = otro.fechaRescision;
        } else {
            finOtro = otro.fechaFin;
        }

        return !fechaInicio.isAfter(finOtro) && !otro.fechaInicio.isAfter(finEfectivo);
    }

}
