package partidos;

import clubes.Club;
import enums.RolEquipo;
import personas.Jugador;
import torneos.JugadorPlantel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NominaPartido implements Serializable {
    private RolEquipo rol;
    private Club club;
    private List<Jugador> jugadoresTitulares;
    private List<Jugador> jugadoresSuplentes;

    public NominaPartido(RolEquipo rol, Club club) {
        if (club == null || rol == null) {
            throw new IllegalArgumentException("No se permite valores nulos");
        }
        this.rol = rol;
        this.club = club;
        jugadoresTitulares = new ArrayList<>();
        jugadoresSuplentes = new ArrayList<>();
    }

    public RolEquipo getRol() {
        return rol;
    }

    public Club getClub() {
        return club;
    }

    public List<Jugador> getJugadoresTitulares() {
        return new ArrayList<>(jugadoresTitulares);
    }

    public List<Jugador> getJugadoresSuplentes() {
        return new ArrayList<>(jugadoresSuplentes);
    }

    public boolean estaConvocado(Jugador jugador) {
        return jugadoresTitulares.contains(jugador) || jugadoresSuplentes.contains(jugador);
    }

    public boolean agregarTitular (JugadorPlantel jugador) {
        if (!puedeConvocarse(jugador)) {
            return false;
        }
        Jugador jugador1 = jugador.getJugador();
        if (!estaConvocado(jugador1) && jugadoresTitulares.size() < 11) {
            jugadoresTitulares.add(jugador1);
            return true;
        }
        return false;
    }

    public boolean esTitular(Jugador jugador) {
        return jugadoresTitulares.contains(jugador);
    }

    public boolean esSuplente(Jugador jugador) {
        return jugadoresSuplentes.contains(jugador);
    }

    public boolean agregarSuplente(JugadorPlantel jugador) {
        if (!puedeConvocarse(jugador)) {
            return false;
        }
        Jugador jugador1 = jugador.getJugador();
        if (!estaConvocado(jugador1)) {
            jugadoresSuplentes.add(jugador1);
            return true;
        }
        return false;
    }

    public boolean tieneFormacionCompleta() {
        if (jugadoresTitulares.size() == 11) {
            return true;
        }
        return false;
    }

    public boolean puedeConvocarse(JugadorPlantel jugadorPlantel) {
        if (jugadorPlantel == null) {
            return false;
        }
        return !jugadorPlantel.estaSuspendido();
    }
}
