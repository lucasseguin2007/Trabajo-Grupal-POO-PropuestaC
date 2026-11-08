import Enums.CanalNotificacion;
import Enums.EstadoEnvio;
import Enums.TipoNotificacion;

import java.util.Date;

public class Notificacion {
    private int minuto;
    private TipoNotificacion tipo;
    private String descripcion;
    private Date fechaEnvio;
    private EstadoEnvio estadoEnvio;
    private CanalNotificacion canal;

    public Notificacion(int minuto, TipoNotificacion tipo, String descripcion, Date fechaEnvio, EstadoEnvio estadoEnvio, CanalNotificacion canal) {
        this.minuto = minuto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaEnvio = fechaEnvio;
        this.estadoEnvio = estadoEnvio;
        this.canal = canal;
    }

    public boolean enviar() {

    }
}
