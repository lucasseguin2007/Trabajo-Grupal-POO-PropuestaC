import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private Club local;
    private Club visitante;
    private Torneo torneo;
    private Estadio estadio;
    private TernaArbitral ternaArbitral;
    private List<Evento> eventos;
    private LocalDateTime fechaHora;
    private EstadoPartido estado;
    private int golesLocal;
    private int golesVisitantes;
    private List<NominaPartido> nominas;

    public Partido(Estadio estadio, LocalDateTime fechaHora, Club local, Club visitante, Torneo torneo, TernaArbitral ternaArbitral) {
        if (local == null || visitante == null || local.equals(visitante)) {
            throw new IllegalArgumentException("Los clubes deben existir y ser distintos");
        }
        if (estadio == null || fechaHora == null || torneo == null || ternaArbitral == null) {
            throw new IllegalArgumentException("El partido debe tener estadio, fecha, torneo y terna arbitral.");
        }
        this.estadio = estadio;
        this.fechaHora = fechaHora;
        this.local = local;
        this.visitante = visitante;
        this.torneo = torneo;
        this.ternaArbitral = ternaArbitral;
        golesLocal = 0;
        golesVisitantes = 0;
        this.estado = EstadoPartido.PROGRAMADO;
        this.eventos = new ArrayList<>();
        this.nominas = new ArrayList<>();
    }

    //Getters
    public Estadio getEstadio() {
        return estadio;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitantes() {
        return golesVisitantes;
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

    public Torneo getTorneo() {
        return torneo;
    }

    public boolean agregarNomina(NominaPartido nomina) {
        if (nomina == null || nominas.size() >= 2 || estado != EstadoPartido.PROGRAMADO) {
            return false;
        }
        if (nomina.getRol() == RolEquipo.LOCAL) {
            if (!nomina.getClub().getNombre().equals(local.getNombre())) {
                return false;
            }
        } else if (nomina.getRol() == RolEquipo.VISITANTE) {
            if (!nomina.getClub().getNombre().equals(visitante.getNombre())) {
                return false;
            }
        } else {
            return false;
        }
        for (NominaPartido n : nominas) {
            if (n.getRol() == nomina.getRol()) {
                return false;
            }
        }
        nominas.add(nomina);
        return true;
    }

    public boolean iniciar() {
        if (estado == EstadoPartido.PROGRAMADO && ternaArbitral != null && nominas.size() == 2) {
            estado = EstadoPartido.EN_JUEGO;
            return true;
        }
        return false;
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
        if (evento instanceof Gol) {
            Gol gol = (Gol) evento;
            actualizarMarcador(gol);
        }
        eventos.add(evento);
        return true;
    }

    public boolean registrarGol(int minuto, Jugador jugador, TipoGol tipo) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }

        if (!estaConvocadoEnPartido(jugador)) {
            return false;
        }

        Gol gol = new Gol(minuto, jugador, tipo);
        return registrarEvento(gol);
    }

    public boolean registrarFalta(int minuto, Jugador jugador) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }
        if (!estaConvocadoEnPartido(jugador)) {
            return false;
        }
        Falta falta = new Falta(minuto, jugador);
        return registrarEvento(falta);
    }

    public boolean registrarTarjetaRoja(int minuto, Jugador jugador, boolean directa) {
        if (estado != EstadoPartido.EN_JUEGO) {
            return false;
        }

        if (!estaConvocadoEnPartido(jugador)) {
            return false;
        }

        TarjetaRoja roja = new TarjetaRoja(minuto, jugador, directa);
        return registrarEvento(roja);
    }

    public boolean registrarCambio() {

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

    private void actualizarMarcador(Gol gol) {
        Club club = obtenerClubJugador(gol.getJugador());
        if (club == null) {
            throw new IllegalArgumentException("El jugador no pertenece a ningún equipo");
        }
        if (gol.getTipo() == TipoGol.EN_CONTRA) {
            if (club.equals(local)){
                golesVisitantes++;
            } else {
                golesLocal++;
            }
        } else {
            if (club.equals(local)) {
                golesLocal++;
            } else {
                golesVisitantes ++;
            }
        }
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
        return enCancha;
    }
}
