package Aplicacion;

import controladores.ControladorPrincipal;
import torneos.GestorTorneos;
import vistas.VistaConsola;


public class MainFutbolStats {
    public static void main(String[] args) {
        GestorTorneos gestorTorneos = new GestorTorneos();
        VistaConsola vista = new VistaConsola();
        ControladorPrincipal controlador = new ControladorPrincipal(vista,gestorTorneos);

        controlador.iniciar();
    }
}
