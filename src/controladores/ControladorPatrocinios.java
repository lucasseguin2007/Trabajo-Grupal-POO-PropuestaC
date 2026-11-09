package controladores;

import torneos.GestorTorneos;
import vistas.VistaConsola;
import patrocinios.Sponsor;
import patrocinios.Patrocinio;
import clubes.Club;
import torneos.Torneo;
import torneos.InscripcionTorneo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class ControladorPatrocinios {
    private VistaConsola vista;
    private GestorTorneos gestorTorneos;
    private List<Sponsor> sponsors;

    public ControladorPatrocinios(VistaConsola vista, GestorTorneos gestorTorneos) {
        this.vista = vista;
        this.gestorTorneos =gestorTorneos;
        this.sponsors = new ArrayList<>();
    }

    public void gestionarPatrocinios() {
        int opcion;
        do {
            vista.mostrarMenuPatrocinios();
            opcion = vista.solicitarOpcion();
            switch (opcion) {
                case 1:
                    registrarSponsor();
                    break;
                case 2:
                    crearPatrocinio();
                    break;
                case 3:
                    consultarPatrocinios();
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
    private void registrarSponsor() {
        String nombre = vista.solicitarTexto("Nombre del sponsor: ");
        String marca = vista.solicitarTexto("Marca: ");
        String logo = vista.solicitarTexto("Logo: ");
        Sponsor sponsor = new Sponsor(nombre, marca, logo);
        sponsors.add(sponsor);
        vista.mostrarMensaje("Sponsor registrado correctamente.");
    }
    private void crearPatrocinio() {
        if (sponsors.isEmpty()) {
            vista.mostrarMensaje("Primero debe registrar un sponsor.");
            return;
        }
        vista.mostrarMensaje("\n=== SPONSORS ===");
        for (int i = 0; i < sponsors.size(); i++) {
            vista.mostrarMensaje((i + 1) + ". " + sponsors.get(i).getNombre());
        }
        vista.mostrarMensaje("Seleccione un sponsor:");
        int numeroSponsor = vista.solicitarOpcion();
        if (numeroSponsor < 1 || numeroSponsor > sponsors.size()) {
            vista.mostrarMensaje("Sponsor inválido.");
            return;
        }
        Sponsor sponsor = sponsors.get(numeroSponsor - 1);
        Torneo torneo = gestorTorneos.getTorneoActual();
        if (torneo == null) {
            vista.mostrarMensaje("No existe un torneo actual.");
            return;
        }
        vista.mostrarMensaje("1. Patrocinar club");
        vista.mostrarMensaje("2. Patrocinar torneo");
        int tipo = vista.solicitarOpcion();
        if (tipo != 1 && tipo != 2) {
            vista.mostrarMensaje("Opción inválida.");
            return;
        }
        Club clubEncontrado = null;
        if (tipo == 1) {
            String nombreClub = vista.solicitarTexto("Nombre del club: ");
            for (InscripcionTorneo inscripcion : torneo.getInscriptos()) {
                if (inscripcion.getClub().getNombre()
                        .equalsIgnoreCase(nombreClub)) {

                    clubEncontrado = inscripcion.getClub();
                    break;
                }
            }
            if (clubEncontrado == null) {
                vista.mostrarMensaje("No se encontró el club.");
                return;
            }
        }
        try {
            double monto = Double.parseDouble(vista.solicitarTexto("Monto del patrocinio: "));
            LocalDate inicio = LocalDate.parse(vista.solicitarTexto("Fecha de inicio (AAAA-MM-DD): "));
            LocalDate fin = LocalDate.parse(vista.solicitarTexto("Fecha de fin (AAAA-MM-DD): "));
            Patrocinio patrocinio;
            if (tipo == 1) {
                patrocinio = new Patrocinio(sponsor, monto, inicio, fin, true, clubEncontrado);
            } else {
                patrocinio = new Patrocinio(sponsor, monto, inicio, fin, true, torneo);
            }
            boolean agregado = sponsor.agregarPatrocinio(patrocinio);
            vista.mostrarMensaje(agregado ? "Patrocinio registrado correctamente." : "No se pudo registrar el patrocinio.");
        } catch (DateTimeParseException e) {
            vista.mostrarMensaje("Error: formato de fecha inválido.");
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("Error: monto inválido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }
    private void consultarPatrocinios() {
        if (sponsors.isEmpty()) {
            vista.mostrarMensaje("No hay sponsors registrados.");
            return;
        }
        vista.mostrarMensaje("\n=== PATROCINIOS REGISTRADOS ===");
        for (Sponsor sponsor : sponsors) {
            for (Patrocinio patrocinio : sponsor.getPatrocinios()) {
                vista.mostrarMensaje("Sponsor: " + sponsor.getNombre());
                if (patrocinio.getClub() != null) {
                    vista.mostrarMensaje("Club patrocinado: " + patrocinio.getClub().getNombre());
                } else if (patrocinio.getTorneo() != null) {
                    vista.mostrarMensaje("Torneo patrocinado: " + patrocinio.getTorneo().getNombre());
                }
                vista.mostrarMensaje("Vigente: " + patrocinio.estaVigente());
                vista.mostrarMensaje("------------------------");
            }
        }
    }

}
