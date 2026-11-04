import java.util.Date;

public class Notificacion {
    private int minuto;
    private TipoNotificacion tipo;
    private String descripcion;
    private Date fechaEnvio;
    private EstadoEnvio estadoEnvio;
    private CanalNotificaion canal;

    public Notificacion(int minuto, TipoNotificacion tipo, String descripcion, Date fechaEnvio, EstadoEnvio estadoEnvio, CanalNotificaion canal) {
        this.minuto = minuto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaEnvio = fechaEnvio;
        this.estadoEnvio = estadoEnvio;
        this.canal = canal;
    }

    public boolean enviar(){

    }
}
