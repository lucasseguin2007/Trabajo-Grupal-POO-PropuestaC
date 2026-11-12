package controladores;

import clubes.Club;
import enums.TipoGol;
import enums.TipoNotificacion;
import notificaciones.Notificacion;
import notificaciones.ServicioNotificaciones;
import notificaciones.Suscriptor;
import partidos.Partido;
import personas.Jugador;
import torneos.Torneo;
import enums.ZonaRemate;

import java.util.ArrayList;
import java.util.List;

public class ControladorPartidos {
    private ServicioNotificaciones servicio;
    private List<Suscriptor> suscriptores;
    public ControladorPartidos(ServicioNotificaciones servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio de notificaciones no puede ser nulo.");
        }
        this.servicio = servicio;
        this.suscriptores = new ArrayList<>();
    }

    public boolean agregarSuscriptor(Suscriptor suscriptor) {
        if (suscriptor == null || suscriptores.contains(suscriptor)) {
            return false;
        }
        suscriptores.add(suscriptor);
        return true;
    }

    private void notificarClubes(Partido partido, Notificacion notificacion) {
        for (Suscriptor suscriptor : suscriptores) {
            if (suscriptor.getClubes().contains(partido.getLocal()) || suscriptor.getClubes().contains(partido.getVisitante())) {
                servicio.notificar(notificacion,suscriptor);
            }
        }
    }

    public boolean registrarGol(Partido partido, int minuto, Jugador jugador, TipoGol tipoGol) {
        if (partido == null) {
            return false;
        }
        boolean registrado = partido.registrarGol(minuto, jugador, tipoGol);
        if (!registrado) {
            return false;
        }
        Notificacion notificacion = new Notificacion(minuto, "Gol registrado en " + partido.getLocal().getNombre() + " vs " + partido.getVisitante().getNombre(), TipoNotificacion.GOL);
        notificarClubes(partido, notificacion);
        return true;
    }
    public boolean registrarTarjetaRoja(Partido partido, int minuto, Jugador jugador) {
        if (partido == null) {
            return false;
        }
        boolean registrado = partido.registrarTarjetaRoja(minuto, jugador);
        if (!registrado) {
            return false;
        }
        Notificacion notificacion = new Notificacion(minuto,"Tarjeta roja para " + jugador.getNombre() + " " + jugador.getApellido(), TipoNotificacion.TARJETA_ROJA);
        notificarClubes(partido,notificacion);
        return true;
    }

    public boolean finalizarPartido(Torneo torneo, Partido partido) {
        if (torneo == null || partido == null) {
            return false;
        }
        boolean finalizado = torneo.finalizarPartido(partido);
        if (!finalizado) {
            return false;
        }
        Notificacion notificacion = new Notificacion(90, "Finalizó el partido " + partido.getLocal().getNombre() + " vs " + partido.getVisitante().getNombre() + ". Resultado: " + partido.getGolesLocal() + " - " + partido.getGolesVisitante(), TipoNotificacion.FINAL_PARTIDO);
        notificarClubes(partido,notificacion);
        return true;
    }

    public boolean registrarTarjetaAmarilla(Partido partido, int minuto, Jugador jugador) {
        if (partido == null || jugador == null) {
            return false;
        }
        boolean registrado = partido.registrarAmarilla(minuto,jugador);
        if (!registrado) {
            return false;
        }
        if (partido.estaExpulsado(jugador)) {
            Notificacion notificacion = new Notificacion(minuto, "Expulsion por doble amarilla: " +
                    jugador.getNombre() + " " + jugador.getApellido(), TipoNotificacion.TARJETA_ROJA);
            notificarClubes(partido,notificacion);
        }
        return true;
    }
    public boolean registrarRemate(Partido partido, int minuto, Jugador jugador, ZonaRemate zona) {
        if (partido == null || jugador == null || zona == null) {
            return false;
        }
        return partido.registrarRemate(minuto,jugador,zona);
    }

    public boolean registrarFalta(Partido partido, int minuto, Jugador jugador) {
        if (partido == null || jugador == null) {
            return false;
        }
        return partido.registrarFalta(minuto, jugador);
    }

    public boolean registrarCambio(Partido partido, int minuto, Jugador jugadorSale, Jugador jugadorEntra) {
        if (partido == null || jugadorSale == null || jugadorEntra == null) {
            return false;
        }
        return partido.registrarCambio(minuto, jugadorSale,jugadorEntra);
    }

}
