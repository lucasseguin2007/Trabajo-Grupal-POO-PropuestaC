package reportes;

import clubes.Club;

public class PosicionTorneo {
    private int partidosJugados;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;
    private int golesAFavor;
    private int golesEnContra;
    private int puntos;
    private Club club;

    public PosicionTorneo(Club club) {
        if (club == null) {
            throw new IllegalArgumentException("El club no puede ser nulo");
        }
        this.club = club;
        this.partidosJugados = 0;
        this.partidosGanados = 0;
        this.partidosEmpatados = 0;
        this.partidosPerdidos = 0;
        this.golesAFavor = 0;
        this.golesEnContra = 0;
        this.puntos = 0;
    }

    public Club getClub() {
        return club;
    }

    public int getGolesAFavor() {
        return golesAFavor;
    }

    public int getGolesEnContra() {
        return golesEnContra;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void registrarResultado(int golesPropios, int golesRivales) {
        if (golesPropios < 0 || golesRivales < 0) {
            throw new IllegalArgumentException(
                    "Los goles no pueden ser negativos"
            );
        }
        partidosJugados++;

        golesAFavor += golesPropios;
        golesEnContra += golesRivales;
        if (golesPropios > golesRivales) {
            partidosGanados ++;
            puntos += 3;
        } else if (golesPropios == golesRivales) {
            partidosEmpatados++;
            puntos++;
        } else {
            partidosPerdidos ++;
        }
    }
    public int calcularDiferenciaGol() {
        return golesAFavor - golesEnContra;
    }
}
