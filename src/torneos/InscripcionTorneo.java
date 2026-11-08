package torneos;

import clubes.Club;
import personas.Jugador;

import java.time.LocalDate;

public class InscripcionTorneo {
    private LocalDate fechaInscripcion;
    private Plantel plantel;
    private Club club;
    private boolean confirmada;
    private boolean bloqueada;

    public InscripcionTorneo(LocalDate fechaInscripcion, Club club) {
        if (fechaInscripcion == null || club == null) {
            throw new IllegalArgumentException("Los datos de la inscripcion no pueden ser nulos");
        }
        this.fechaInscripcion = fechaInscripcion;
        this.plantel = new Plantel();
        this.club = club;
        this.confirmada = false;
        this.bloqueada = false;
    }

    public Club getClub() {
        return club;
    }

    public boolean estaConfirmada() {
        return confirmada;
    }

    public Plantel getPlantel() {
        return plantel;
    }

    public boolean estaBloqueada() {
        return bloqueada;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean confirmarInscripcion() {
        if (confirmada || bloqueada) {
            return false;
        }
        if (!plantel.tieneCantidadValida()) {
            return false;
        }
        confirmada = true;
        return true;
    }

    public boolean bloquearPlantel() {
        if (!confirmada || bloqueada) {
            return false;
        }
        bloqueada = true;
        return true;
    }

    public boolean agregarJugador(int numero, Jugador jugador) {
        if (bloqueada) {
            return false;
        }
        return plantel.agregarJugador(numero,jugador,club);
    }

    public boolean quitarJugador(Jugador jugador) {
        if (bloqueada) {
            return false;
        }
        if (confirmada && plantel.cantidadJugadores() <= 18) {
            return false;
        }
        return plantel.quitarJugador(jugador);
    }
}
