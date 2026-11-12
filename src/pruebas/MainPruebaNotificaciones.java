package pruebas;
import clubes.Club;
import enums.TipoNotificacion;
import notificaciones.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainPruebaNotificaciones {
    public static void main(String[] args) {
        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Suscriptor lucas = new Suscriptor("Lucas", "lucas@gmail.com", null);
        lucas.seguirClub(boca);
        CanalApp app = new CanalApp();
        CanalCorreo correo = new CanalCorreo();
        CanalSMS sms = new CanalSMS();
        lucas.configurarCanal(app);
        lucas.configurarCanal(correo);
        lucas.configurarCanal(sms);
        List<CanalEnvio> canales = new ArrayList<>();
        canales.add(app);
        canales.add(correo);
        canales.add(sms);
        ServicioNotificaciones servicio = new ServicioNotificaciones(canales);
        Notificacion notificacion = new Notificacion(35, "Gol de Boca convertido por Juan Pérez", TipoNotificacion.GOL);
        servicio.notificar(notificacion, lucas);
        System.out.println("Historial de envíos:");
        for (EnvioNotificacion envio : servicio.getHistorialEnvios()) {
            System.out.println("Estado: " + envio.getEstado());
            if (envio.getDetalleError() != null) {
                System.out.println("Error: " + envio.getDetalleError());
            }
        }
        System.out.println("Total de intentos: " + servicio.getHistorialEnvios().size());
    }
}
