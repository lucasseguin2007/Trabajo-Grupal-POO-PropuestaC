package torneos;

import clubes.Club;
import clubes.Contrato;
import personas.Jugador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plantel implements Serializable {
    private List<JugadorPlantel> jugadoresPlantel;
//metodo constructor
    public Plantel() {
        this.jugadoresPlantel = new ArrayList<>();
    }
    //metodos
     boolean agregarJugador(int numero, Jugador jugador, Club club){
        if (jugador == null || club == null) {
            return false;
        }
        if (numero < 1 || numero > 99) {
            return false;
        }
        if (jugadoresPlantel.size() >= 30) {
            return false;
        }

        Contrato contrato = jugador.obtenerContratoVigente();
        if (contrato == null || contrato.getClub() != club) {
            return false;
        }
        for (JugadorPlantel jp: jugadoresPlantel) {
            if (jp.getJugador().equals(jugador)) {
                return false;
            }
            if (jp.getNumeroCamiseta() == numero) {
                return false;
            }
        }
        JugadorPlantel nuevo = new JugadorPlantel(numero,jugador);
        jugadoresPlantel.add(nuevo);
        return true;
    }

    public boolean tieneCantidadValida() {
        return jugadoresPlantel.size() >= 18 && jugadoresPlantel.size() <= 30;
    }

    public  int cantidadJugadores() {
        return jugadoresPlantel.size();
    }

    public List<JugadorPlantel> getJugadoresPlantel() {
        return new ArrayList<>(jugadoresPlantel);
    }
    boolean quitarJugador(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        for (int i = 0; i<jugadoresPlantel.size(); i++) {
            if (jugadoresPlantel.get(i).getJugador().equals(jugador)) {
                jugadoresPlantel.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean estaInscripto(Jugador jugador) {
        if (jugador == null) {
            return false;
        }

        for (JugadorPlantel jp: jugadoresPlantel) {
            if (jp.getJugador().equals(jugador)) {
                return true;
            }
        }
        return false;
    }

    public JugadorPlantel buscarJugador(Jugador jugador) {
        if (jugador == null) {
            return null;
        }
        for (JugadorPlantel jp : jugadoresPlantel) {
            if (jp.getJugador().equals(jugador)) {
                return jp;
            }
        }
        return null;
    }
}
