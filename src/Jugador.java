import Enums.Posicion;

import java.time.LocalDate;

public class Jugador extends Persona{
    private Posicion posicion;
    private double valorMercado;
    private int partidosJugados;
    private int goles;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private List<Sancion> sanciones;
    private Contrato contratos[];

    public Jugador(int dni, String nombre, String apellido, LocalDate fechaNacimiento, int goles, int partidosJugados, int tarjetasAmarillas, int tarjetasRojas, Posicion posicion, double valorMercado) {
        super(dni, nombre, apellido, fechaNacimiento);
        if (goles < 0 || partidosJugados < 0 || tarjetasAmarillas < 0 || tarjetasRojas < 0) {
            throw new IllegalArgumentException("Las estadísticas del juegador no pueden ser negativa");
        }
        this.goles = goles;
        this.partidosJugados = partidosJugados;
        this.tarjetasAmarillas = tarjetasAmarillas;
        this.tarjetasRojas = tarjetasRojas;
        this.posicion = posicion;
        this.valorMercado = valorMercado;
        this.sanciones = new List<Sancion>();
        this.contratos = new Contrato[20];
    }

    //Getters and Setters

    public int getGoles() {
        return goles;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(double valorMercado) {
        this.valorMercado = valorMercado;
    }

    //Metodos

    @Override
    public String obtenerDescripcion() {
        return "Jugador: " + getNombre() + " " + getApellido()
                + " | Posición: " + posicion
                + " | Goles: " + goles
                + " | Tarjetas Amarillas: " + tarjetasAmarillas
                + " | Partidos jugados: " + partidosJugados;
    }

    public void registrarGol(){
        this.goles ++;
    }

    public void registrarAmarilla(){
        this.tarjetasAmarillas++;
    }

    public void registrarRoja(){
        this.tarjetasRojas++;
    }

    public void registrarPartidoJugado() {
        this.partidosJugados ++;
    }


}
