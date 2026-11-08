package notificaciones;

import enums.CanalNotificacion;
import enums.EstadoEnvio;
import enums.TipoNotificacion;

import java.time.LocalDateTime;
import java.util.Date;

public class Notificacion {
    private int minuto;
    private TipoNotificacion tipo;
    private String descripcion;
    private LocalDateTime fechaCreacion;

    public Notificacion(int minuto, String descripcion, TipoNotificacion tipo) {
        if (minuto < 0 || minuto > 120) {
            throw new IllegalArgumentException("El minuto debe estar entre 0 y 120");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de notificación no puede ser nulo");
        }
        this.minuto = minuto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now();

    }
    //Getters
    public int getMinuto() {
        return minuto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    //Metodo
    public String obtenerMensaje() {
        return tipo + " - Minuto " + minuto + ": " + descripcion;
    }

}
