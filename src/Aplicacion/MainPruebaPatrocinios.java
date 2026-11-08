package Aplicacion;

import clubes.Club;
import patrocinios.Patrocinio;
import patrocinios.Sponsor;
import torneos.Torneo;

import java.time.LocalDate;

public class MainPruebaPatrocinios {

    public static void main(String[] args) {

        // 1. Crear clubes y torneo
        Club boca = new Club(
                "Boca",
                LocalDate.of(1905, 4, 3),
                null
        );

        Club river = new Club(
                "River",
                LocalDate.of(1901, 5, 25),
                null
        );

        Torneo torneo = new Torneo(
                "Liga Argentina",
                LocalDate.now(),
                LocalDate.now().plusMonths(6)
        );

        // 2. Crear sponsors
        Sponsor adidas = new Sponsor(
                "Adidas",
                "Adidas",
                "adidas.png"
        );

        Sponsor nike = new Sponsor(
                "Nike",
                "Nike",
                "nike.png"
        );

        // 3. Crear patrocinios
        Patrocinio patrocinioBoca = new Patrocinio(
                adidas,
                1000000,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                true,
                boca
        );

        Patrocinio patrocinioLiga = new Patrocinio(
                adidas,
                2000000,
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                true,
                torneo
        );

        Patrocinio patrocinioRiver = new Patrocinio(
                nike,
                1500000,
                LocalDate.now(),
                LocalDate.now().plusYears(2),
                true,
                river
        );

        // 4. Registrar patrocinios
        System.out.println("=== REGISTRO ===");

        System.out.println("Adidas - Boca: "
                + adidas.agregarPatrocinio(patrocinioBoca));

        System.out.println("Adidas - Liga: "
                + adidas.agregarPatrocinio(patrocinioLiga));

        System.out.println("Nike - River: "
                + nike.agregarPatrocinio(patrocinioRiver));

        // 5. Probar validaciones
        System.out.println("\n=== VALIDACIONES ===");

        System.out.println("Patrocinio duplicado: "
                + adidas.agregarPatrocinio(patrocinioBoca));

        System.out.println("Sponsor incorrecto: "
                + nike.agregarPatrocinio(patrocinioBoca));

        // 6. Consultar patrocinios
        System.out.println("\n=== CONSULTAS ===");

        System.out.println("Patrocinios de Adidas: "
                + adidas.getPatrocinios().size());

        System.out.println("Patrocinios de Nike: "
                + nike.getPatrocinios().size());

        System.out.println("Patrocinio Boca vigente: "
                + patrocinioBoca.estaVigente());

        System.out.println("Patrocinio Liga vigente: "
                + patrocinioLiga.estaVigente());
    }
}