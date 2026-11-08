package notificaciones;

import java.util.ArrayList;
import java.util.List;

public class ServicioNotificaciones {
    private List<CanalEnvio> canales;
    private List<EnvioNotificacion> historialEnvios;
    public ServicioNotificaciones(List<CanalEnvio> canales) {
        if (canales == null) {
            throw new IllegalArgumentException("La lista de canales no puede ser nula");
        }
        this.canales = new ArrayList<>(canales);
        this.historialEnvios = new ArrayList<>();
    }

    public List<EnvioNotificacion> getHistorialEnvios() {
        return historialEnvios;
    }

    public void notificar(Notificacion notificacion, Suscriptor suscriptor) {
        if (notificacion == null || suscriptor == null) {
            return;
        }
        for (CanalEnvio canal: canales) {
            for (CanalEnvio configurado : suscriptor.getCanales()) {
                if (canal.getClass() == configurado.getClass()) {
                    EnvioNotificacion envio = new EnvioNotificacion();
                    try {
                        boolean enviado = canal.enviar(notificacion, suscriptor);
                        if (enviado) {
                            envio.marcarEnviada();
                        } else {
                            envio.marcarFallida(
                                    "No se pudo enviar por "
                                            + canal.getClass().getSimpleName()
                            );
                        }
                    } catch (Exception e) {
                        envio.marcarFallida(e.getMessage());
                    }
                    historialEnvios.add(envio);
                }
            }
        }
    }
}
