package clubes;

import personas.Jugador;

import java.time.LocalDate;

public class GestorContratos {
    public boolean registrarAlta (Jugador jugador, Club club, LocalDate fechaInicio, LocalDate fechaFin, double sueldo) {
        if (jugador == null || club == null || fechaInicio == null || fechaFin == null) {
            return false;
        }
        if (fechaInicio.isAfter(LocalDate.now()) || fechaFin.isBefore(LocalDate.now())) {
            return false;
        }
        if (fechaFin.isBefore(fechaInicio) || sueldo < 0) {
            return false;
        }
        Contrato contrato = new Contrato(fechaInicio, fechaFin, sueldo, club);
        if (!jugador.agregarContrato(contrato)) {
            return false;
        }
        club.vincularJugador(jugador);

        return true;
    }

    public boolean rescindirContrato(Jugador jugador) {
        if (jugador == null) {
            return false;
        }

        Contrato contrato = jugador.obtenerContratoVigente();

        if(contrato == null) {
            return false;
        }
        return contrato.rescindir();
    }

    public boolean transferirJugador(Jugador jugador, Club clubDestino, LocalDate fechaFinNueva, double sueldoNuevo ) {
        if (jugador == null || clubDestino == null || fechaFinNueva == null || sueldoNuevo < 0) {
            return false;
        }
        Contrato contratoActual = jugador.obtenerContratoVigente();
        if (contratoActual == null) {
            return false;
        }
        Club clubOrigen = contratoActual.getClub();
        if (clubOrigen == clubDestino) {
            return false;
        }
        LocalDate inicioNuevo = LocalDate.now();
        if (fechaFinNueva.isBefore(inicioNuevo)) {
            return false;
        }
        Contrato contratoNuevo = new Contrato(inicioNuevo,fechaFinNueva,sueldoNuevo,clubDestino);
        for (Contrato contrato: jugador.getContratos()) {
            if (contrato != contratoActual && contrato.seSuperpone(contratoNuevo)) {
                return false;
            }
        }

        if(!contratoActual.rescindir()) {
            return false;
        }
        if (!jugador.agregarContrato(contratoNuevo)) {
            contratoActual.deshacerRescision();
            return false;
        }
        clubDestino.vincularJugador(jugador);
        return true;
    }
}
