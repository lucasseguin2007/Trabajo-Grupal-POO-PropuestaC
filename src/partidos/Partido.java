package partidos;

import clubes.Club;
import enums.EstadoPartido;
import enums.RolEquipo;
import enums.TipoGol;
import enums.ZonaRemate;
import eventos.*;
import personas.Arbitro;
import personas.Jugador;
import torneos.Torneo;
import torneos.JugadorPlantel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partido implements Serializable {
    private Club local;
    private Club visitante;
    private Estadio estadio;
    private TernaArbitral ternaArbitral;
    private List<Evento> eventos;
    private LocalDateTime fechaHora;
    private EstadoPartido estado;
    private List<NominaPartido> nominas;
    private int jornada;
    //Metodo constructor
    public Partido(Club local, Club visitante, int jornada) {
        if (local == null || visitante == null || local.equals(visitante) ) {
            throw new IllegalArgumentException("Los clubes deben existir y ser distintos");
        }
        if (jornada < 1) {
            throw new IllegalArgumentException("La jornada debe ser mayor a cero");
        }
        this.local =local;
        this.visitante = visitante;
        this.jornada = jornada;
        this.estadio = null;
        this.fechaHora = null;
        this.ternaArbitral = null;
        this.estado = EstadoPartido.PROGRAMADO;
        this.eventos = new ArrayList<>();
        this.nominas = new ArrayList<>();
    }

    //Getters
    public int getJornada() {
        return jornada;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public List<Evento> getEventos() {
        return new ArrayList<>(eventos);
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public Club getLocal() {
        return local;
    }

    public Club getVisitante() {
        return visitante;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public TernaArbitral getTernaArbitral() {
        return ternaArbitral;
    }

    public List<NominaPartido> getNominas() {
        return nominas;
    }

    //Metodos
        public boolean programarPartido(Torneo torneo, LocalDateTime fechaHora, Estadio estadio, Arbitro arbitro1, Arbitro arbitro2, Arbitro arbitro3) {
        if (torneo == null) {
            return false;
        }
        if (!torneo.puedeProgramarPartido(this,fechaHora,estadio,arbitro1,arbitro2,arbitro3)){
            return false;
        }
        if (estado != EstadoPartido.PROGRAMADO) {
            return false;
        }
        if (fechaHora == null || estadio == null || arbitro1 == null || arbitro2 == null || arbitro3 == null) {
            return false;
        }
        if (arbitro1 == arbitro2 || arbitro1 == arbitro3 || arbitro2 == arbitro3) {
            return false;
        }
        TernaArbitral nuevaTerna = new TernaArbitral();
        if (!nuevaTerna.agregarArbitro(arbitro1) || !nuevaTerna.agregarArbitro(arbitro2) || !nuevaTerna.agregarArbitro(arbitro3)) {
            return false;
        }
        this.fechaHora = fechaHora;
        this.estadio = estadio;
        this.ternaArbitral = nuevaTerna;
        return true;
    }

    public boolean estaCompletamenteProgramado() {
        return fechaHora != null && estadio != null && ternaArbitral != null && ternaArbitral.estaCompleta();
    }

    public boolean agregarNomina(RolEquipo rol, Club club) {
        if (rol == null || estado != EstadoPartido.PROGRAMADO || club == null) {
            return false;
        }
        if (nominas.size() >= 2){
            return false;
        }
        if (rol == RolEquipo.LOCAL && !club.equals(local)) {
            return false;
        }
        if (rol == RolEquipo.VISITANTE && !club.equals(visitante)) {
            return false;
        }
        for (NominaPartido n : nominas) {
            if (n.getRol() == rol) {
                return false;
            }
        }
        NominaPartido nomina = new NominaPartido(rol,club);
        nominas.add(nomina);
        return true;
    }

    public boolean agregarTitular (RolEquipo rol, JugadorPlantel jugador) {
        NominaPartido nomina = obtenerNominaValida(rol,jugador);
        if (nomina == null) {
            return false;
        }
        return nomina.agregarTitular(jugador);
    }

    public boolean agregarSuplente (RolEquipo rol, JugadorPlantel jugador) {
        NominaPartido nomina = obtenerNominaValida(rol,jugador);
        if (nomina == null) {
            return false;
        }
        return nomina.agregarSuplente(jugador);
    }

    public boolean iniciar() {
        if (estado != EstadoPartido.PROGRAMADO) {
            return false;
        }
        if (!estaCompletamenteProgramado()) {
            return false;
        }
        if (nominas.size() != 2) {
            return false;
        }
         for (NominaPartido nomina: nominas) {
             if(!nomina.tieneFormacionCompleta()){
                return false;
            }
         }
        estado = EstadoPartido.EN_JUEGO;
        return true;
    }

    public void finalizar() {
        if (estado != EstadoPartido.EN_JUEGO) {
            throw new IllegalStateException("Solamente se puede finalizar un partido que siga en juego");
        }
        estado = EstadoPartido.FINALIZADO;
    }

    private boolean registrarEvento(Evento evento) {
        if (estado != EstadoPartido.EN_JUEGO || evento == null) {
            return false;
        }
        if (!estaConvocadoEnPartido(evento.getJugador())) {
            return false;
        }
        eventos.add(evento);
        return true;
    }

    public boolean registrarGol(int minuto, Jugador jugador, TipoGol tipo) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }

        if (!estaEnCancha(jugador)) {
            return false;
        }

        Gol gol = new Gol(minuto, jugador, tipo);
        return registrarEvento(gol);
    }

    public boolean registrarFalta(int minuto, Jugador jugador) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }
        if (!estaEnCancha(jugador)) {
            return false;
        }
        Falta falta = new Falta(minuto, jugador);
        return registrarEvento(falta);
    }

    public boolean registrarCambio(int minuto, Jugador jugadorSale, Jugador jugadorEntra) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }
        if (jugadorSale == null || jugadorEntra == null || jugadorSale.equals(jugadorEntra)) {
            return false;
        }
        if (!estaConvocadoEnPartido(jugadorSale) || !estaConvocadoEnPartido(jugadorEntra)) {
            return false;
        }
        Club clubSale = obtenerClubJugador(jugadorSale);
        Club clubEntra = obtenerClubJugador(jugadorEntra);
        if (clubSale == null || clubEntra == null || !clubSale.equals(clubEntra)  ) {
            return false;
        }
        if (!estaEnCancha(jugadorSale)) {
            return false;
        }
        boolean esSuplente = false;
        for (NominaPartido nomina: nominas) {
            if (nomina.getClub().equals(clubEntra)) {
                esSuplente = nomina.esSuplente(jugadorEntra);
            }
        }
        if (!esSuplente) {
            return false;
        }
        if (yaIngreso(jugadorEntra) || yaFueSustituido(jugadorEntra)) {
            return false;
        }
        if (contarCambios(clubSale) >= 5 ) {
            return false;
        }
        if (estaExpulsado(jugadorSale) || estaExpulsado(jugadorEntra)) {
            return false;
        }
        Cambio cambio = new Cambio(minuto, jugadorSale, jugadorEntra);
        return registrarEvento(cambio);

    }

    public boolean registrarAmarilla(int minuto, Jugador jugador) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }
        if (!estaEnCancha(jugador)) {
            return false;
        }
        TarjetaAmarilla tarjeta = new TarjetaAmarilla(minuto, jugador);
        if (!registrarEvento(tarjeta)) {
            return false;
        }
        if (contarAmarillas(jugador) >= 2) {
            TarjetaRoja tarjetaRoja = new TarjetaRoja(minuto,jugador,false);
            return registrarEvento(tarjetaRoja);
        }
        return true;

    }

    public boolean registrarTarjetaRoja(int minuto, Jugador jugador) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }
        if (!estaEnCancha(jugador)) {
            return false;
        }
        TarjetaRoja tarjetaRoja = new TarjetaRoja(minuto,jugador,true);
        return registrarEvento(tarjetaRoja);
    }

    public boolean registrarRemate(int minuto, Jugador jugador, ZonaRemate zona) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }
        if (!estaEnCancha(jugador)) {
            return false;
        }
        Remate remate = new Remate(minuto,jugador,zona);
        return registrarEvento(remate);
    }

    private int contarAmarillas(Jugador jugador) {
        int cantidad = 0;
        for (Evento evento: eventos) {
            if (evento instanceof TarjetaAmarilla) {
                if (evento.getJugador().equals(jugador)) {
                    cantidad ++;
                }
            }
        }
        return cantidad;
    }

    public boolean estaConvocadoEnPartido(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        for (NominaPartido n : nominas) {
            if (n.estaConvocado(jugador)) {
                return true;
            }
        }
        return false;
    }

    public Club obtenerClubJugador(Jugador jugador) {
        for (NominaPartido nomina: nominas) {
            if(nomina.estaConvocado(jugador)) {
                return nomina.getClub();
            }
        }
        return null;
    }

    public boolean estaEnCancha(Jugador jugador) {
        if (jugador == null || !estaConvocadoEnPartido(jugador)) {
            return false;
        }
        boolean enCancha = false;
        for (NominaPartido nomina : nominas) {
            if (nomina.esTitular(jugador)) {
                enCancha = true;
            }
        }
        for (Evento evento: eventos) {
            if (evento instanceof Cambio) {
                Cambio cambio = (Cambio) evento;
                if (cambio.getJugador().equals(jugador)) {
                    enCancha = false;
                } else if (cambio.getJugadorEntra().equals(jugador))
                    enCancha = true;
            }
        }
        return enCancha && !estaExpulsado(jugador);
    }

    private boolean yaFueSustituido(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        for (Evento evento: eventos) {
            if (evento instanceof Cambio) {
                Cambio cambio = (Cambio) evento;
                if (cambio.getJugador().equals(jugador)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean yaIngreso(Jugador jugador) {
        if (jugador == null) {
            return false;
        }

        for (Evento evento: eventos) {
            if (evento instanceof Cambio) {
                Cambio cambio = (Cambio) evento;
                if (cambio.getJugadorEntra().equals(jugador)){
                    return true;
                }
            }
        }
        return false;
    }

    private int contarCambios(Club club) {
        int cantidad = 0;
        for (Evento evento: eventos) {
            if (evento instanceof Cambio) {
                Cambio cambio = (Cambio) evento;
                Jugador jugador = cambio.getJugador();
                Club club1 = obtenerClubJugador(jugador);
                if (club1 != null && club1.equals(club)  ) {
                    cantidad ++;
                }
            }
        }
        return cantidad;
    }

    public boolean estaExpulsado(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        for (Evento evento: eventos) {
            if (evento instanceof TarjetaRoja) {
                if(evento.getJugador().equals(jugador)) {
                    return true;
                }
            }
        }
        return false;
    }
    private int calcularGoles(Club club) {
        int goles = 0;
        for (Evento evento: eventos) {
            if (evento instanceof  Gol) {
                Gol gol = (Gol) evento;
                if (estaAnulado(gol)) {
                    continue;
                }
                Club clubJugador = obtenerClubJugador(gol.getJugador());
                if (clubJugador == null ) {
                    continue;
                }

                if (gol.getTipo() == TipoGol.EN_CONTRA) {
                    if(!clubJugador.equals(club)) {
                        goles ++;
                    }
                } else {
                    if (clubJugador.equals(club)) {
                        goles ++;
                    }
                }
            }
        }
        return goles;
    }

    private boolean estaAnulado(Evento eventoOriginal) {
        for (Evento evento: eventos) {
            if (evento instanceof AnulacionEvento) {
                AnulacionEvento anulacion = (AnulacionEvento) evento;
                if (anulacion.getEventoOriginal() == eventoOriginal) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getGolesLocal() {
        return calcularGoles(local);
    }

    public int getGolesVisitante() {
        return calcularGoles(visitante);
    }

    public boolean anularGol (Gol gol, String motivo) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }
        if (gol == null || motivo == null || motivo.isBlank()) {
            return false;
        }

        if (!eventos.contains(gol)) {
            return false;
        }
        if (estaAnulado(gol)) {
            return false;
        }
        AnulacionEvento anulacion = new AnulacionEvento(gol,motivo);
        return registrarEvento(anulacion);
    }

    public List<Gol> getGolesValido() {
        List<Gol> goles = new ArrayList<>();
        for (Evento evento: eventos) {
            if (evento instanceof Gol) {
                Gol gol = (Gol) evento;
                if (!estaAnulado(gol)) {
                    goles.add(gol);
                }
            }
        }
        return goles;
    }
    private NominaPartido obtenerNominaValida(RolEquipo rol, JugadorPlantel jugador) {
        if (estado != EstadoPartido.PROGRAMADO || rol == null || jugador == null) {
            return null;
        }
        for (NominaPartido nomina: nominas) {
            if (nomina.getRol() != rol) {
                continue;
            }
            Jugador jugadorActual = jugador.getJugador();
            if (jugadorActual.obtenerContratoVigente() == null) {
                return null;
            }
            if (jugadorActual.obtenerContratoVigente().getClub() != nomina.getClub()) {
                return null;
            }
            return nomina;
        }
        return null;
    }
}
