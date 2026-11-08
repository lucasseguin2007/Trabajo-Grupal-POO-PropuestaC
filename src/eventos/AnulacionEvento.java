package eventos;

import java.time.LocalDateTime;

public class AnulacionEvento extends Evento{
    private String motivo;
    private LocalDateTime fechaAnulacion;
    private Evento eventoOriginal;

    public AnulacionEvento(Evento eventoOriginal, String motivo) {
        super(obtenerMinuto(eventoOriginal), eventoOriginal.getJugador());
        this.eventoOriginal = eventoOriginal;
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Debe haber un motivo");
        }
        this.motivo = motivo;
        this.fechaAnulacion = LocalDateTime.now();
    }

    private static int obtenerMinuto(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo");
        }
        return evento.getMinuto();
    }

    public Evento getEventoOriginal() {
        return eventoOriginal;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getFechaAnulacion() {
        return fechaAnulacion;
    }

    @Override
    public String obtenerDescripcion() {
        return "Eventos.Evento anulado: " + eventoOriginal.obtenerDescripcion()
                + " | Fecha: " + fechaAnulacion
                + " | motivo: " + motivo;
    }
}
