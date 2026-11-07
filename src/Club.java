import Enums.Posicion;

import java.time.LocalDate;
import java.util.Date;

public class Club {
    private String nombre;
    private Date fechaFundacion;
    private InscripcionTorneo inscripcion;
    private Jugador jugadores[];
    private Estadio estadio;

public Club(String nombre, Date fechaFundacion, InscripcionTorneo inscripcion, Jugador[] jugadores, Estadio estadio) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.inscripcion = inscripcion;
        this.jugadores = new Jugador[50];
        this.estadio = estadio;
    }
public void agregarJugador(int dni, String nombre, String apellido, LocalDate fechaNacimiento, Posicion posicion, double valorMercado, int partidosJugados, int goles, int tarjetasAmarillas, int tarjetasRojas){
    Jugador j = new Jugador(dni, nombre, apellido, fechaNacimiento,goles,partidosJugados,tarjetasAmarillas, tarjetasRojas, posicion,valorMercado);
    for (int i = 0; i < jugadores.length; i++) {
        if (jugadores[i] == null) {
            jugadores[i] = j;
            return;
        }
    }
}
public void anotarInscripcion(InscripcionTorneo it){
    if (inscripcion == null){
                inscripcion = it;
            }
    System.out.println("Máximo de torneos alcanzado");
}

    public String getNombre() {
        return this.nombre;
    }
}
