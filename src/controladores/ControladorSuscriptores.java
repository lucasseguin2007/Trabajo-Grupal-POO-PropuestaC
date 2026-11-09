package controladores;
import clubes.Club;
import notificaciones.CanalApp;
import notificaciones.Suscriptor;
import torneos.GestorTorneos;
import torneos.InscripcionTorneo;
import torneos.Torneo;
import vistas.VistaConsola;

import java.util.ArrayList;
import java.util.List;

public class ControladorSuscriptores {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private ControladorPartidos controladorPartidos;
    private List<Suscriptor> suscriptores;

    public ControladorSuscriptores(ControladorPartidos controladorPartidos, VistaConsola vista, GestorTorneos gestorTorneos) {
        this.controladorPartidos = controladorPartidos;
        this.vista = vista;
        this.gestorTorneos = gestorTorneos;
        this.suscriptores = new ArrayList<>();
    }
    public void gestionarSuscriptores() {
        int opcion;
        do {
            vista.mostrarMenuSuscriptores();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    registrarSuscriptor();
                    break;
                case 2:
                    seguirClub();
                    break;
                case 3:
                    consultarSuscriptores();
                    break;
                case 0:
                    vista.mostrarMensaje("Volviendo al menú principal...");
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }
    private void registrarSuscriptor() {
        String nombre = vista.solicitarTexto("Nombre: ");
        String correo = vista.solicitarTexto("Correo: ");
        String telefono = vista.solicitarTexto("Teléfono: ");
        Suscriptor suscriptor = new Suscriptor(nombre, correo, telefono);
        suscriptor.configurarCanal(new CanalApp());
        boolean registrado = controladorPartidos.agregarSuscriptor(suscriptor);
        if (registrado) {
            suscriptores.add(suscriptor);
            vista.mostrarMensaje("Suscriptor registrado correctamente.");
        } else {
            vista.mostrarMensaje("No se pudo registrar el suscriptor.");
        }
    }
    private void seguirClub() {
        if (suscriptores.isEmpty()) {
            vista.mostrarMensaje("No hay suscriptores registrados.");
            return;
        }
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        vista.mostrarMensaje("\n=== SUSCRIPTORES ===");
        for (int i = 0; i < suscriptores.size(); i++) {
            vista.mostrarMensaje((i + 1) + ". " + suscriptores.get(i).getNombre()
            );
        }
        vista.mostrarMensaje("Seleccione un suscriptor:");
        int numero = vista.solicitarOpcion();
        if (numero < 1 || numero > suscriptores.size()) {
            vista.mostrarMensaje("Suscriptor inválido.");
            return;
        }
        Suscriptor suscriptor = suscriptores.get(numero - 1);
        String nombreClub = vista.solicitarTexto("Nombre del club: ");
        for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
            Club club = inscripcion.getClub();
            if (club.getNombre().equalsIgnoreCase(nombreClub)) {
                suscriptor.seguirClub(club);
                vista.mostrarMensaje(suscriptor.getNombre() + " ahora sigue a " + club.getNombre());
                return;
            }
        }
        vista.mostrarMensaje("No se encontró el club.");
    }
    private void consultarSuscriptores() {
        if (suscriptores.isEmpty()) {
            vista.mostrarMensaje("No hay suscriptores registrados.");
            return;
        }
        vista.mostrarMensaje("\n=== SUSCRIPTORES ===");
        for (Suscriptor suscriptor : suscriptores) {
            vista.mostrarMensaje("Nombre: " + suscriptor.getNombre());
            vista.mostrarMensaje("Correo: " + suscriptor.getCorreo());
            vista.mostrarMensaje("Clubes seguidos:");
            if (suscriptor.getClubes().isEmpty()) {
                vista.mostrarMensaje("Ninguno.");
            } else {
                for (Club club : suscriptor.getClubes()) {
                    vista.mostrarMensaje("- " + club.getNombre());
                }
            }
            vista.mostrarMensaje("--------------------");
        }
    }
}
