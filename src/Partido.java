import Enums.EstadoPartido;
import Enums.RolEquipo;
import Enums.TipoGol;
import Enums.ZonaRemate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private Club local;
    private Club visitante;
    private Estadio estadio;
    private TernaArbitral ternaArbitral;
    private List<Evento> eventos;
    private LocalDateTime fechaHora;
    private EstadoPartido estado;
    private List<NominaPartido> nominas;

    public Partido(Estadio estadio, LocalDateTime fechaHora, Club local, Club visitante, TernaArbitral ternaArbitral) {
        if (local == null || visitante == null || local.equals(visitante)) {
            throw new IllegalArgumentException("Los clubes deben existir y ser distintos");
        }
        if (estadio == null || fechaHora == null || ternaArbitral == null) {
            throw new IllegalArgumentException("El partido debe tener estadio, fecha, torneo y terna arbitral.");
        }
        this.estadio = estadio;
        this.fechaHora = fechaHora;
        this.local = local;
        this.visitante = visitante;
        this.ternaArbitral = ternaArbitral;
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

    public boolean iniciar() {
        if (estado == EstadoPartido.PROGRAMADO && ternaArbitral != null || nominas.size() == 2) {
            for (NominaPartido nomina: nominas) {
                if(!nomina.tieneFormacionCompleta()) {
                    return false;
                }
            }
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
}
