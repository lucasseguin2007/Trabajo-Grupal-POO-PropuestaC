package pruebas;

import clubes.Club;
import patrocinios.Patrocinio;
import patrocinios.Sponsor;
import torneos.Torneo;
import java.time.LocalDate;

public class MainPruebaPatrocinios {
    public static void main(String[] args) {

        LocalDate hoy = LocalDate.now();
        Club boca = new Club("Boca", LocalDate.of(1905, 4, 3), null);
        Club river = new Club("River", LocalDate.of(1901, 5, 25), null);
        Torneo torneo = new Torneo("Liga Argentina", hoy, hoy.plusMonths(6));
        Sponsor adidas = new Sponsor("Adidas", "Adidas", "adidas.png");
        Sponsor nike = new Sponsor("Nike", "Nike", "nike.png");
        Patrocinio patrocinioBoca = new Patrocinio(adidas, 1000000, hoy, hoy.plusYears(2), true, boca);
        Patrocinio patrocinioLiga = new Patrocinio(adidas, 2000000, hoy, hoy.plusYears(1), true, torneo);
        Patrocinio patrocinioRiver = new Patrocinio(nike, 1500000, hoy, hoy.plusYears(2), true, river);

        System.out.println("Registro de patrocinios:");
        System.out.println("Adidas - Boca: " + adidas.agregarPatrocinio(patrocinioBoca));
        System.out.println("Adidas - Liga: " + adidas.agregarPatrocinio(patrocinioLiga));
        System.out.println("Nike - River: " + nike.agregarPatrocinio(patrocinioRiver));
        System.out.println("\nPruebas de validación:");
        System.out.println("Patrocinio duplicado: " + adidas.agregarPatrocinio(patrocinioBoca));
        System.out.println("Sponsor incorrecto: " + nike.agregarPatrocinio(patrocinioBoca));
        System.out.println("\nCantidad de patrocinios:");
        System.out.println("Adidas: " + adidas.getPatrocinios().size());
        System.out.println("Nike: " + nike.getPatrocinios().size());
        System.out.println("\nPatrocinios vigentes:");
        System.out.println("Boca: " + patrocinioBoca.estaVigente());
        System.out.println("Liga: " + patrocinioLiga.estaVigente());
    }
}
