package personas;

import clubes.Contrato;
import enums.Posicion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jugador extends Persona {
    private Posicion posicion;
    private double valorMercado;
    private List<Contrato> contratos;

    //Metodo Constructor
    public Jugador(int dni, String nombre, String apellido, LocalDate fechaNacimiento, Posicion posicion, double valorMercado) {
        super(dni, nombre, apellido, fechaNacimiento);
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        if (valorMercado < 0) {
            throw new IllegalArgumentException("El valor de mercado no puede ser negativo");
        }
        this.posicion = posicion;
        this.valorMercado = valorMercado;
        this.contratos = new ArrayList<>();
    }

    //Getters and Setters

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        this.posicion = posicion;
    }

    public double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(double valorMercado) {
        if (valorMercado < 0) {
            throw new IllegalArgumentException("El valor de mercado no puede ser negativo");
        }
        this.valorMercado = valorMercado;
    }

    public List<Contrato> getContratos() {
        return new ArrayList<>(contratos);
    }

    //Metodos
    @Override
    public String obtenerDescripcion() {
        return "Personas.Jugador: " + getNombre() + " " + getApellido()
                + " | Posición: " + posicion;
    }

    public Contrato obtenerContratoVigente() {
        for (Contrato contrato: contratos) {
            if (contrato.estaVigente()) {
                return contrato;
            }
        }
        return null;
    }

    public boolean agregarContrato(Contrato contrato) {
        if (contrato == null || contratos.contains(contrato)) {
            return false;
        }
        for (Contrato existente : contratos) {
            if (existente.seSuperpone(contrato)) {
                return false;
            }
        }

        contratos.add(contrato);
        return true;
    }


}
