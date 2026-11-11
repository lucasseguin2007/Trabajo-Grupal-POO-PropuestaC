package vistas;

import java.util.Scanner;
public class VistaConsola {
    private Scanner scanner;
    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }
    public void mostrarMenuPrincipal() {
        System.out.println("\n================================");
        System.out.println("          FUTBOLSTATS");
        System.out.println("================================");
        System.out.println("1. Gestionar torneos");
        System.out.println("2. Gestionar clubes y jugadores");
        System.out.println("3. Gestionar partidos");
        System.out.println("4. Gestionar contratos");
        System.out.println("5. Gestionar patrocinios");
        System.out.println("6. Consultar reportes");
        System.out.println("7. Gestionar suscriptores");
        System.out.println("8. Guardar datos");
        System.out.println("9. Cargar datos");
        System.out.println("0. Salir");
        System.out.println("================================");
    }
    public int solicitarOpcion() {
        System.out.println("Seleccione una opcion: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String solicitarTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarMenuTorneo() {
        System.out.println("\n===== GESTIÓN DE TORNEOS =====");
        System.out.println("1. Crear nuevo torneo");
        System.out.println("2. Consultar torneo actual");
        System.out.println("3. Consultar torneos anteriores");
        System.out.println("4. Inscribir club al torneo");
        System.out.println("5. Consultar clubes inscriptos");
        System.out.println("6. Confirmar inscripción de un club");
        System.out.println("7. Generar fixture");
        System.out.println("0. Volver al menú principal");
        System.out.println("==============================");
    }

    public void mostrarMenuJugadores() {
        System.out.println("\n===== GESTIÓN DE JUGADORES Y CLUBES =====");
        System.out.println("1. Agregar jugador a un plantel");
        System.out.println("2. Consultar jugadores de un plantel");
        System.out.println("3. Crear club");
        System.out.println("0. Volver al menú principal");
        System.out.println("=========================================");
    }

    public void mostrarMenuPartidos() {
        System.out.println("\n===== GESTIÓN DE PARTIDOS =====");
        System.out.println("1. Consultar fixture");
        System.out.println("2. Programar partido");
        System.out.println("3. Convocar jugadores");
        System.out.println("4. Iniciar partido");
        System.out.println("5. Registrar gol");
        System.out.println("6. Registrar tarjeta roja");
        System.out.println("7. Finalizar partido");
        System.out.println("0. Volver al menú principal");
        System.out.println("===============================");
    }

    public void mostrarMenuReportes() {
        System.out.println("\n===== REPORTES =====");
        System.out.println("1. Tabla de posiciones");
        System.out.println("2. Tabla de goleadores");
        System.out.println("3. Equipos menos goleados");
        System.out.println("4. Proyección por promedio");
        System.out.println("5. Proyección por simulación xG");
        System.out.println("0. Volver al menú principal");
        System.out.println("====================");
    }

    public void mostrarMenuPatrocinios() {
        System.out.println("\n===== PATROCINIOS =====");
        System.out.println("1. Registrar sponsor");
        System.out.println("2. Crear patrocinio");
        System.out.println("3. Consultar patrocinios");
        System.out.println("0. Volver al menú principal");
        System.out.println("=======================");
    }

    public void mostrarMenuSuscriptores() {
        System.out.println("\n===== SUSCRIPTORES =====");
        System.out.println("1. Registrar suscriptor");
        System.out.println("2. Seguir un club");
        System.out.println("3. Consultar suscriptores");
        System.out.println("0. Volver al menú principal");
        System.out.println("========================");
    }

    public void mostrarMenuContratos() {
        System.out.println("\n===== GESTIÓN DE CONTRATOS =====");
        System.out.println("1. Consultar contratos de un jugador");
        System.out.println("2. Rescindir contrato");
        System.out.println("3. Transferir jugador");
        System.out.println("0. Volver al menú principal");
        System.out.println("================================");
    }


}
