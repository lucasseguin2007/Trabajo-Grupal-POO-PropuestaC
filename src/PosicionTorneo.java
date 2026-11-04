public class PosicionTorneo {
    private int partidosJugados;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;
    private int golesAFavor;
    private int golesEnContra;
    private int puntos;

    public PosicionTorneo(int partidosJugados, int partidosGanados, int partidosEmpatados, int partidosPerdidos, int golesAFavor, int golesEnContra, int puntos) {
        this.partidosJugados = 0;
        this.partidosGanados = 0;
        this.partidosEmpatados = 0;
        this.partidosPerdidos = 0;
        this.golesAFavor = 0;
        this.golesEnContra = 0;
        this.puntos = 0;
    }

    public int calcularDiferenciaGol(){
        return golesAFavor - golesEnContra;
    }
}
