package notificaciones;

public interface CanalEnvio {
    boolean enviar(Notificacion notificacion, Suscriptor suscriptor);
}
