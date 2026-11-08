package controladores;

import clubes.Club;
import torneos.GestorTorneos;
import vistas.VistaConsola;

import clubes.GestorContratos;
import personas.Jugador;
import enums.Posicion;
import torneos.JugadorPlantel;
import torneos.InscripcionTorneo;
import torneos.Torneo;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import notificaciones.CanalEnvio;
import notificaciones.CanalApp;
import notificaciones.ServicioNotificaciones;

import java.util.ArrayList;
import java.util.List;

public class ControladorPrincipal {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorContratos gestorContratos;
    private ControladorTorneos controladorTorneos;
    private ControladorJugadores controladorJugadores;
    private ControladorMenuPartidos controladorMenuPartidos;


    public ControladorPrincipal(VistaConsola vista, GestorTorneos gestorTorneos) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        List<CanalEnvio> canales = new ArrayList<>();
        canales.add(new CanalApp());
        ServicioNotificaciones servicio = new ServicioNotificaciones(canales);
        ControladorPartidos controladorPartidos = new ControladorPartidos(servicio);
        this.controladorMenuPartidos = new ControladorMenuPartidos(vista, gestorTorneos, controladorPartidos);
        this.gestorContratos = new GestorContratos();
        this.controladorTorneos = new ControladorTorneos(vista,gestorTorneos);
        this.controladorJugadores = new ControladorJugadores(vista,gestorTorneos,gestorContratos);
    }

    public void iniciar() {
        int opcion;
        do {
            vista.mostrarMenuPrincipal();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    controladorTorneos.gestionarTorneos();
                    break;
                case 2:
                    controladorJugadores.gestionarJugadores();
                    break;
                case 3:
                    controladorMenuPartidos.gestionarPartidos();
                    break;
                case 4:
                    vista.mostrarMensaje("Gestión de contratos.");
                    break;
                case 5:
                    vista.mostrarMensaje("Gestion de contratos");
                    break;
                case 6:
                    vista.mostrarMensaje("Consulta de reportes");
                    break;
                case 7:
                    vista.mostrarMensaje("Gestión de suscriptores.");
                    break;

                case 8:
                    vista.mostrarMensaje("Guardar datos.");
                    break;

                case 9:
                    vista.mostrarMensaje("Cargar datos.");
                    break;

                case 0:
                    vista.mostrarMensaje("Saliendo de FutbolStats...");
                    break;

                default:
                    vista.mostrarMensaje("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }

}
