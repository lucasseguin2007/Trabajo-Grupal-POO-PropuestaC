package controladores;

import persistencia.GestorArchivosTorneo;
import torneos.GestorTorneos;
import vistas.VistaConsola;
import clubes.GestorClubes;

import java.io.IOException;
public class ControladorPersistencia {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorArchivosTorneo gestorArchivos;
    private final String RUTA = "torneos.dat";
    private GestorClubes gestorClubes;

    public ControladorPersistencia(VistaConsola vista, GestorTorneos gestorTorneos, GestorClubes gestorClubes) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.gestorArchivos = new GestorArchivosTorneo();
        this.gestorClubes = gestorClubes;
    }

    public void guardarDatos() {
        try {
            gestorArchivos.guardarTorneos(gestorTorneos, RUTA);
            vista.mostrarMensaje("Datos guardados correctamente.");
        } catch (IOException e) {
            vista.mostrarMensaje("Error al guardar los datos: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        try {
            GestorTorneos gestorCargado = gestorArchivos.cargarTorneos(RUTA);
            gestorTorneos.cargarDatos(gestorCargado);
            gestorClubes.sincronizarClubesConTorneo(gestorTorneos.getTorneoActual());
            vista.mostrarMensaje("Datos cargados correctamente.");
        } catch (IOException e) {
            vista.mostrarMensaje("Error al leer el archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            vista.mostrarMensaje("Error: no se pudo recuperar una clase del archivo.");
        } catch (ClassCastException e) {
            vista.mostrarMensaje("Error: el archivo contiene datos incompatibles.");
        }
    }
}
