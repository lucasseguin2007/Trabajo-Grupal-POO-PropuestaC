package Aplicacion;

import clubes.Club;
import enums.TipoNotificacion;
import notificaciones.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainPruebaNotificaciones {

    public static void main(String[] args) {

        // 1. Crear un club
        Club boca = new Club(
                "Boca",
                LocalDate.of(1905, 4, 3),
                null
        );

        // 2. Crear un suscriptor
        Suscriptor lucas = new Suscriptor(
                "Lucas",
                "lucas@gmail.com",
                null
        );

        lucas.seguirClub(boca);

        // 3. Configurar canales
        lucas.configurarCanal(new CanalApp());
        lucas.configurarCanal(new CanalCorreo());
        lucas.configurarCanal(new CanalSMS());

        // 4. Crear los canales disponibles
        List<CanalEnvio> canales = new ArrayList<>();

        canales.add(new CanalApp());
        canales.add(new CanalCorreo());
        canales.add(new CanalSMS());

        // 5. Crear el servicio
        ServicioNotificaciones servicio =
                new ServicioNotificaciones(canales);

        // 6. Crear una notificación
        Notificacion notificacion = new Notificacion(
                35,
                "Gol de Boca convertido por Juan Pérez",
                TipoNotificacion.GOL
        );

        // 7. Enviar
        System.out.println("=== ENVÍO DE NOTIFICACIONES ===");

        servicio.notificar(notificacion, lucas);

        // 8. Mostrar resultados
        System.out.println("\n=== HISTORIAL DE ENVÍOS ===");

        for (EnvioNotificacion envio : servicio.getHistorialEnvios()) {

            System.out.println("Estado: " + envio.getEstado());

            if (envio.getDetalleError() != null) {
                System.out.println("Error: " + envio.getDetalleError());
            }
        }

        System.out.println("\nTotal de intentos: "
                + servicio.getHistorialEnvios().size());
    }
}