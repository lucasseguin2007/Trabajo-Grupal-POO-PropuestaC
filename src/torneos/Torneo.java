package torneos;

import clubes.Club;
import enums.EstadoPartido;
import enums.EstadoTorneo;
import enums.MotivoSancion;
import enums.RolEquipo;
import eventos.Evento;
import eventos.TarjetaAmarilla;
import eventos.TarjetaRoja;
import partidos.Estadio;
import partidos.Partido;
import personas.Arbitro;
import personas.Jugador;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Torneo implements Serializable {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoTorneo estado;
    private List<Partido> partidos;
    private List<InscripcionTorneo> inscriptos;
    //Metodo constructor
    public Torneo(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de finalización debe ser posterior a la de inicio");
        }
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoTorneo.PLANIFICADO;
        this.inscriptos = new ArrayList<>();
        this.partidos = new ArrayList<>();
    }

    //getter
    public String getNombre() {
        return nombre;
    }
    public EstadoTorneo getEstado() {
        return estado;
    }
    public List<InscripcionTorneo> getInscriptos() {
        return new ArrayList<>(inscriptos);
    }
    public List<Partido> getPartidos() {
        return new ArrayList<>(partidos);
    }

    public boolean inscribirClub (Club club) {
        if (club == null) {
            return false;
        }
        if (estado != EstadoTorneo.PLANIFICADO) {
            return false;
        }

        for (InscripcionTorneo inscripcion : inscriptos) {
            if (inscripcion.getClub().equals(club)) {
                return false;
            }
        }
        InscripcionTorneo nuevaInscripcion = new InscripcionTorneo(LocalDate.now(),club);
        inscriptos.add(nuevaInscripcion);
        return true;
    }

    public boolean iniciarTorneo() {
        if (estado != EstadoTorneo.PLANIFICADO) {
            return false;
        }
        if (inscriptos.size() < 4) {
            return false;
        }
        for (InscripcionTorneo inscripcion: inscriptos) {
            if (!inscripcion.estaConfirmada()) {
                return false;
            }
            if (!inscripcion.getPlantel().tieneCantidadValida()) {
                return false;
            }
        }
        int cantidadClubes = inscriptos.size();
        int partidosEsperados = cantidadClubes * (cantidadClubes-1);

        if (partidos.size() != partidosEsperados) {
            return false;
        }

        for (Partido partido: partidos) {
            if (!partido.estaCompletamenteProgramado()) {
                return false;
            }
        }

        for (InscripcionTorneo inscripcion: inscriptos) {
            inscripcion.bloquearPlantel();
        }

        estado = EstadoTorneo.EN_CURSO;
        return true;
    }

    public boolean finalizarTorneo() {
        if (estado != EstadoTorneo.EN_CURSO) {
            return false;
        }
        if (partidos.isEmpty()) {
            return false;
        }
        for (Partido partido : partidos) {
            if (partido.getEstado() != EstadoPartido.FINALIZADO) {
                return false;
            }
        }
        estado = EstadoTorneo.FINALIZADO;
        return true;
    }

    public boolean finalizarPartido(Partido partido) {
        if (partido == null || !partidos.contains(partido)) {
            return false;
        }
        if (partido.getEstado() != EstadoPartido.EN_JUEGO) {
            return false;
        }
        for (InscripcionTorneo inscripcion : inscriptos) {
            Club club = inscripcion.getClub();
            if (!club.equals(partido.getLocal()) && !club.equals(partido.getVisitante())) {
                continue;
            }
            for (JugadorPlantel jp: inscripcion.getPlantel().getJugadoresPlantel()) {
                jp.cumplirFechaSuspension();
            }
        }
        partido.finalizar();
        for (InscripcionTorneo inscripcion : inscriptos) {
            Club club = inscripcion.getClub();
            if (!club.equals(partido.getLocal()) && !club.equals(partido.getVisitante())) {
                continue;
            }
            for (JugadorPlantel jp: inscripcion.getPlantel().getJugadoresPlantel()) {
                Jugador jugador = jp.getJugador();
                boolean expulsado = false;
                int amarillasPartido = 0;
                for (Evento evento: partido.getEventos()) {
                    if (!evento.getJugador().equals(jugador)) {
                        continue;
                    }
                    if (evento instanceof TarjetaRoja) {
                        expulsado = true;
                    }
                    if (evento instanceof TarjetaAmarilla) {
                        amarillasPartido++;
                    }
                }
                int totalAmarillas = contarAmarillasTorneo(jugador);
                boolean acumulacion = totalAmarillas / 5 > (totalAmarillas - amarillasPartido) / 5;
                if (expulsado || acumulacion) {
                    MotivoSancion motivo;
                    if (expulsado) {
                        motivo = MotivoSancion.EXPULSION;
                    } else {
                        motivo = MotivoSancion.ACUMULACION_AMARILLAS;
                    }
                    Sancion sancion = new Sancion(1,motivo,jp,club);
                    jp.agregarSancion(sancion);
                }
            }
        }
        return true;
    }

    private int contarAmarillasTorneo(Jugador jugador) {
        int cantidad = 0;
        for (Partido partido: partidos) {
            if (partido.getEstado() != EstadoPartido.FINALIZADO) {
                continue;
            }
            for (Evento evento: partido.getEventos()) {
                if (evento instanceof TarjetaAmarilla && evento.getJugador().equals(jugador)) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    public List<Partido> generarFixture() {
        if (estado != EstadoTorneo.PLANIFICADO) {
            return new ArrayList<>();
        }
        if (!partidos.isEmpty() || inscriptos.size() < 4) {
            return new ArrayList<>();
        }
        List<Club> clubes = new ArrayList<>();

        for (InscripcionTorneo inscripcion : inscriptos) {
            if (!inscripcion.estaConfirmada() || !inscripcion.getPlantel().tieneCantidadValida()) {
                return new ArrayList<>();
            }
            clubes.add(inscripcion.getClub());
        }
        if (clubes.size() % 2 != 0) {
            clubes.add(null);
        }

        int cantidad = clubes.size();
        int jornadasIda = cantidad -1;
        int partidosPorJornada = cantidad /2;
        List<Partido> ida = new ArrayList<>();
        List<Partido> vuelta = new ArrayList<>();
        for (int jornada = 1; jornada <= jornadasIda; jornada++) {
            for (int i = 0; i < partidosPorJornada; i++) {
                Club equipo1 = clubes.get(i);
                Club equipo2 = clubes.get(cantidad-1-i);
                if (equipo1 == null || equipo2 == null) {
                    continue;
                }
                Club local;
                Club visitante;

                if ((jornada + i) % 2 == 0) {
                    local = equipo1;
                    visitante = equipo2;
                } else {
                    local = equipo2;
                    visitante = equipo1;
                }
                ida.add(new Partido(local,visitante,jornada));
                vuelta.add(new Partido(visitante,local,jornada+ jornadasIda));
            }
            Club ultimo = clubes.remove(cantidad - 1);
            clubes.add(1,ultimo);
        }
        partidos.addAll(ida);
        partidos.addAll(vuelta);
        return new ArrayList<>(partidos);
    }

    public boolean puedeProgramarPartido(Partido partido, LocalDateTime fechaHora, Estadio estadio, Arbitro arbitro1, Arbitro arbitro2, Arbitro arbitro3) {
        if (estado != EstadoTorneo.PLANIFICADO) {
            return false;
        }
        if (partido == null || fechaHora == null || estadio == null
                || arbitro1 == null || arbitro2 == null || arbitro3 == null) {
            return false;
        }
        if (!partidos.contains(partido)) {
            return false;
        }
        LocalDate fecha = fechaHora.toLocalDate();
        if (fecha.isBefore(fechaInicio) || fecha.isAfter(fechaFin)) {
            return false;
        }
        if (!tieneDescanso(partido, fechaHora)) {
            return false;
        }
        if (!estadioDisponible(partido, estadio, fechaHora)) {
            return false;
        }
        if (!arbitrosDisponibles(partido,fechaHora,arbitro1,arbitro2,arbitro3)) {
            return false;
        }
        return true;
    }

    public boolean programarPartido(Partido partido, LocalDateTime fechaHora, Estadio estadio, Arbitro arbitro1, Arbitro arbitro2, Arbitro arbitro3) {
        if (partido == null) {
            return false;
        }
        return partido.programarPartido(this,fechaHora,estadio,arbitro1,arbitro2,arbitro3);
    }

    private boolean tieneDescanso(Partido partido, LocalDateTime nuevaFecha) {
        for (Partido otroPartido : partidos) {
            if (otroPartido == partido) {
                continue;
            }
            if (otroPartido.getFechaHora() == null) {
                continue;
            }

            boolean compartenClub = otroPartido.getLocal().equals(partido.getLocal())
                    || otroPartido.getVisitante().equals(partido.getLocal())
                    || otroPartido.getLocal().equals(partido.getVisitante())
                    || otroPartido.getVisitante().equals(partido.getVisitante());

            if (compartenClub) {
                long minutos = Math.abs(Duration.between(otroPartido.getFechaHora(),nuevaFecha).toMinutes());
                if (minutos < 48 * 60) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean estadioDisponible(Partido partido, Estadio estadio, LocalDateTime nuevaFecha) {
        LocalDateTime finNuevo = nuevaFecha.plusHours(3);
        for (Partido otroPartido : partidos) {
            if (otroPartido == partido) {
                continue;
            }
            if (otroPartido.getFechaHora() == null
                    || otroPartido.getEstadio() == null) {
                continue;
            }
            if (otroPartido.getEstadio().getNombre().equalsIgnoreCase(estadio.getNombre())) {
                LocalDateTime inicioExistente = otroPartido.getFechaHora();
                LocalDateTime finExistente =  inicioExistente.plusHours(3);
                boolean haySuperposicion = nuevaFecha.isBefore(finExistente) && inicioExistente.isBefore(finNuevo);
                if (haySuperposicion) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean arbitrosDisponibles(Partido partido, LocalDateTime nuevaFecha, Arbitro arbitro1, Arbitro arbitro2, Arbitro arbitro3) {
        Arbitro[] nuevosArbitros = {arbitro1, arbitro2, arbitro3};
        for (Arbitro arbitro : nuevosArbitros) {
            if (arbitro == null) {
                return false;
            }
            Club clubHincha = arbitro.getClubHincha();
            if (clubHincha != null && (clubHincha.equals(partido.getLocal()) || clubHincha.equals(partido.getVisitante()))) {
                return false;
            }
        }
        for (Partido otroPartido : partidos) {
            if (otroPartido == partido) {
                continue;
            }
            if (otroPartido.getFechaHora() == null || otroPartido.getTernaArbitral() == null) {
                continue;
            }
            if (!otroPartido.getFechaHora().toLocalDate().equals(nuevaFecha.toLocalDate())) {
                continue;
            }
            Arbitro[] arbitrosAsignados = otroPartido.getTernaArbitral().getArbitros();
            for (Arbitro nuevo : nuevosArbitros) {
                for (Arbitro asignado : arbitrosAsignados) {
                    if (asignado != null && nuevo.getDni() == asignado.getDni()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean jugadorInscripto(Partido partido, RolEquipo rol, Jugador jugador) {
        if (partido == null || rol == null || jugador == null) {
            return false;
        }
        if (!partidos.contains(partido)) {
            return false;
        }
        Club club;
        if (rol == RolEquipo.LOCAL) {
            club = partido.getLocal();
        } else {
            club = partido.getVisitante();
        }
        for (InscripcionTorneo inscripcion : inscriptos) {
            if (inscripcion.getClub().equals(club)) {
                return inscripcion.estaConfirmada()
                        && inscripcion.getPlantel().estaInscripto(jugador);
            }
        }
        return false;
    }

    public boolean convocarTitular(Partido partido, RolEquipo rol, JugadorPlantel jugador) {
        if (!puedeConvocar(partido,rol,jugador)) {
            return false;
        }
        return partido.agregarTitular(rol,jugador);
    }

    public boolean convocarSuplente(Partido partido, RolEquipo rol, JugadorPlantel jugador) {
        if (!puedeConvocar(partido,rol,jugador)) {
            return false;
        }
        return partido.agregarSuplente(rol,jugador);
    }
    private boolean puedeConvocar(Partido partido, RolEquipo rol,JugadorPlantel jugador) {
        if (partido == null || rol == null || jugador == null) {
            return false;
        }
        if (!jugadorInscripto(partido,rol,jugador.getJugador())) {
            return false;
        }
        boolean perteneceAlplantel = false;
        for (InscripcionTorneo inscripcion: inscriptos) {
            Club club = inscripcion.getClub();
            if (!club.equals(partido.getLocal()) && !club.equals(partido.getVisitante())) {
                continue;
            }
            if (inscripcion.getPlantel().buscarJugador(jugador.getJugador()) == jugador) {
                perteneceAlplantel = true;
                break;
            }
        }
        if (!perteneceAlplantel) {
            return false;
        }
        return !jugador.estaSuspendido();
    }
}
