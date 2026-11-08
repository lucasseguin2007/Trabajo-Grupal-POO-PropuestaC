package patrocinios;

import clubes.Club;
import torneos.Torneo;
import java.time.LocalDate;

public class Patrocinio {
    private Sponsor sponsor;
    private Club club;
    private Torneo torneo;
    private double monto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean estado;

    public Patrocinio(Sponsor sponsor, double monto, LocalDate fechaInicio, LocalDate fechaFin, Boolean estado, Club club) {
        if (sponsor == null || club == null) {
            throw new IllegalArgumentException("El sponsor y el club son obligatorios");
        }

        if (fechaInicio == null || fechaFin == null
                || fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("Las fechas no son válidas");
        }

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        this.club = club;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.monto = monto;
        this.estado = estado;
        this.sponsor = sponsor;
        this.torneo = null;

    }
    public Patrocinio(Sponsor sponsor, double monto, LocalDate fechaInicio, LocalDate fechaFin, Boolean estado, Torneo torneo) {
        if (sponsor == null || torneo == null) {
            throw new IllegalArgumentException("El sponsor y el torneo son obligatorios");
        }
        if (fechaInicio == null || fechaFin == null
                || fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("Las fechas no son válidas");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        this.sponsor = sponsor;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.club = null;
        this.torneo = torneo;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public Club getClub() {
        return club;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public boolean estaVigente(){
        LocalDate hoy = LocalDate.now();
        return Boolean.TRUE.equals(estado) && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

}
