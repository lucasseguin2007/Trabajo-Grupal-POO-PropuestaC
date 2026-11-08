package notificaciones;

public class CanalApp implements CanalEnvio{
    @Override
    public boolean enviar(Notificacion notificacion, Suscriptor suscriptor) {
        if (notificacion == null || suscriptor == null) {
            return false;
        }
        System.out.println("[APP] Notificacion para " + suscriptor.getNombre()
        + ": " + notificacion.obtenerMensaje());
        return true;
    }
}
