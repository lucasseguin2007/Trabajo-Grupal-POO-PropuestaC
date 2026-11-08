package notificaciones;

public class CanalCorreo implements CanalEnvio{
    @Override
    public boolean enviar(Notificacion notificacion, Suscriptor suscriptor) {
        if (notificacion == null || suscriptor == null) {
            return false;
        }
        if (suscriptor.getCorreo() == null || !suscriptor.getCorreo().contains("@")) {
            return false;
        }
        System.out.println("[CORREO] Enviando a " + suscriptor.getCorreo() + ": " + notificacion.obtenerMensaje());

        return true;
    }
}
