package Aplicacion;

import clubes.Club;
import enums.MotivoSancion;
import enums.Posicion;
import enums.RolEquipo;
import partidos.NominaPartido;
import personas.Jugador;
import torneos.JugadorPlantel;
import torneos.Sancion;

import java.time.LocalDate;

public class MainPruebaSanciones {

    public static void main(String[] args) {

        Club boca = new Club(
                "Boca",
                LocalDate.of(1905, 4, 3),
                null
        );

        Jugador jugador = new Jugador(
                30000000,
                "Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                Posicion.DEFENSOR,
                100000
        );

        JugadorPlantel jp = new JugadorPlantel(4, jugador);

        NominaPartido nomina = new NominaPartido(
                RolEquipo.LOCAL,
                boca
        );

        // Registrar una sanción de una fecha
        Sancion sancion = new Sancion(
                1,
                MotivoSancion.EXPULSION,
                jp,
                boca
        );

        System.out.println("Sanción registrada: "
                + jp.agregarSancion(sancion));

        System.out.println("Jugador suspendido: "
                + jp.estaSuspendido());

        // Intentar convocarlo estando suspendido
        System.out.println("Convocatoria suspendido: "
                + nomina.agregarTitular(jp));

        // Cumplir la fecha
        jp.cumplirFechaSuspension();

        System.out.println("Jugador suspendido después: "
                + jp.estaSuspendido());

        // Intentar convocarlo nuevamente
        System.out.println("Convocatoria habilitado: "
                + nomina.agregarTitular(jp));
    }
}