package notificaciones;

import enums.EstadoEnvio;

import java.time.LocalDateTime;

public class EnvioNotificacion {
    private LocalDateTime fechaIntento;
    private EstadoEnvio estado;
    private String detalleError;

    public EnvioNotificacion() {
        this.fechaIntento = LocalDateTime.now();
        this.estado = EstadoEnvio.PENDIENTE;
        this.detalleError = null;
    }

    public String getDetalleError() {
        return detalleError;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public LocalDateTime getFechaIntento() {
        return fechaIntento;
    }
    public void marcarEnviada() {
        this.estado = EstadoEnvio.ENVIADA;
        this.detalleError = null;
    }

    public void marcarFallida(String detalleError) {
        this.estado = EstadoEnvio.FALLIDA;
        this.detalleError = detalleError;
    }
}
