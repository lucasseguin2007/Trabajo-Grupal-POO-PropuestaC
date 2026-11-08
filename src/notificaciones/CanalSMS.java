package notificaciones;

public class CanalSMS implements CanalEnvio{
    @Override
    public boolean enviar(Notificacion notificacion, Suscriptor suscriptor) {
        if (notificacion == null || suscriptor == null) {
            return false;
        }
        if (suscriptor.getTelefono() == null || suscriptor.getTelefono().isBlank()) {
            return false;
        }
        System.out.println("[SMS] Enviando a " + suscriptor.getTelefono()
        + ": " + notificacion.obtenerMensaje());
        return true;
    }
}
