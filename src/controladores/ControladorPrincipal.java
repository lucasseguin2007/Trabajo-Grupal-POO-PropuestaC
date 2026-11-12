package controladores;

import clubes.Club;
import clubes.GestorClubes;
import notificaciones.*;
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

import java.util.ArrayList;
import java.util.List;

public class ControladorPrincipal {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorContratos gestorContratos;
    private ControladorTorneos controladorTorneos;
    private ControladorJugadores controladorJugadores;
    private ControladorMenuPartidos controladorMenuPartidos;
    private ControladorReportes controladorReportes;
    private ControladorPatrocinios controladorPatrocinios;
    private ControladorSuscriptores controladorSuscriptores;
    private ControladorContratos controladorContratos;
    private ControladorPersistencia controladorPersistencia;
    private GestorClubes gestorClubes;

    public ControladorPrincipal(VistaConsola vista, GestorTorneos gestorTorneos) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        List<CanalEnvio> canales = new ArrayList<>();
        canales.add(new CanalApp());
        canales.add(new CanalCorreo());
        canales.add(new CanalSMS());
        ServicioNotificaciones servicio = new ServicioNotificaciones(canales);
        ControladorPartidos controladorPartidos = new ControladorPartidos(servicio);
        this.controladorSuscriptores = new ControladorSuscriptores(controladorPartidos, vista, gestorTorneos);
        this.controladorMenuPartidos = new ControladorMenuPartidos(vista, gestorTorneos, controladorPartidos);
        this.gestorContratos = new GestorContratos();
        this.gestorClubes = new GestorClubes();
        this.controladorTorneos = new ControladorTorneos(vista, gestorTorneos, gestorClubes);
        this.controladorJugadores = new ControladorJugadores(vista, gestorTorneos, gestorContratos, gestorClubes);this.controladorReportes = new ControladorReportes(vista,gestorTorneos);
        this.controladorPatrocinios = new ControladorPatrocinios(vista,gestorTorneos);
        this.controladorContratos = new ControladorContratos(vista,gestorTorneos,gestorContratos);
        this.controladorPersistencia = new ControladorPersistencia(vista, gestorTorneos);
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
                    controladorContratos.gestionarContratos();
                    break;
                case 5:
                    controladorPatrocinios.gestionarPatrocinios();
                    break;
                case 6:
                    controladorReportes.gestionarReportes();
                    break;
                case 7:
                    controladorSuscriptores.gestionarSuscriptores();
                    break;
                case 8:
                    controladorPersistencia.guardarDatos();
                    break;
                case 9:
                    controladorPersistencia.cargarDatos();
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
