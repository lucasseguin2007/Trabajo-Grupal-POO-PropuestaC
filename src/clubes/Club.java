package clubes;

import partidos.Estadio;
import personas.Entrenador;
import personas.Jugador;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Club implements Serializable {
    private String nombre;
    private LocalDate fechaFundacion;
    private List<Jugador> jugadores;
    private Estadio estadio;
    private Entrenador entrenador;

    //Metodo constructor
    public Club(String nombre, LocalDate fechaFundacion, Estadio estadio) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del club no puede esta vacío");
        }
        if (fechaFundacion == null) {
            throw new IllegalArgumentException("La fecha fundacion no puede ser nula.");
        }
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.jugadores = new ArrayList<>();
        this.estadio = estadio;
    }
    //Getters and Setters
    public String getNombre() {
        return this.nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public List<Jugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    public Entrenador entrenador() {
        return entrenador;
    }

    // Metodos

    public Entrenador getEntrenador() {
        return entrenador;
    }
    public boolean asignarEntrenador(Entrenador entrenador) {
        if (entrenador == null) {
            return false;
        }
        this.entrenador = entrenador;
        return true;
    }
    public boolean contieneJugador(Jugador jugador) {
        return jugadores.contains(jugador);
    }

     boolean vincularJugador (Jugador jugador) {
        if (jugador == null) {
            return false;
        }

        if (!jugadores.contains(jugador)) {
            jugadores.add(jugador);
        }
        return true;
    }

    public List<Jugador> getJugadoresActuales() {
        List<Jugador> actuales = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            Contrato contrato = jugador.obtenerContratoVigente();

            if (contrato != null && contrato.getClub() == this) {
                actuales.add(jugador);
            }
        }
        return actuales;
    }
}

