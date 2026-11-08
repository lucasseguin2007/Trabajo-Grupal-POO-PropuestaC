package torneos;

import clubes.Club;
import enums.MotivoSancion;

import java.time.LocalDate;

public class Sancion {
    private int fechasSuspension;
    private int fechasCumplidas;
    private LocalDate fechaRegistro;
    private MotivoSancion motivo;
    private JugadorPlantel jugador;
    private Club club;

    public Sancion(int fechasSuspension, MotivoSancion motivo, JugadorPlantel jugador, Club club) {
        if (fechasSuspension <= 0) {
            throw new IllegalArgumentException("La fecha de suspension debe ser mayor a 0");
        }
        if (jugador == null || motivo == null || club == null) {
            throw new IllegalArgumentException("No se admiten valores nulos");
        }
        this.fechasSuspension = fechasSuspension;
        this.motivo = motivo;
        this.jugador = jugador;
        this.club = club;
        fechaRegistro = LocalDate.now();
        fechasCumplidas = 0;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public int getFechasCumplidas() {
        return fechasCumplidas;
    }

    public int getFechasSuspension() {
        return fechasSuspension;
    }

    public JugadorPlantel getJugador() {
        return jugador;
    }

    public Club getClub() {
        return club;
    }


    public MotivoSancion getMotivo() {
        return motivo;
    }

    public boolean estaVigente() {
        return fechasCumplidas < fechasSuspension;
    }

    public void cumplirFecha() {
        if (estaVigente()) {
            fechasCumplidas ++;
        }
    }

}
