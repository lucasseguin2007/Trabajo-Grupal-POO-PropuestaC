package controladores;

import persistencia.GestorArchivosTorneo;
import torneos.GestorTorneos;
import vistas.VistaConsola;

import java.io.IOException;
public class ControladorPersistencia {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private GestorArchivosTorneo gestorArchivos;
    private final String RUTA = "torneos.dat";

    public ControladorPersistencia(VistaConsola vista, GestorTorneos gestorTorneos) {
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.gestorArchivos = new GestorArchivosTorneo();
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
