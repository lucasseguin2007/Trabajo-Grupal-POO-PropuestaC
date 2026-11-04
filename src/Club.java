import Enums.Posicion;

import java.time.LocalDate;
import java.util.Date;

public class Club {
    private String nombre;
    private Date fechaFundacion;
    private InscripcionTorneo inscriptos[];
    private Jugador jugadores[];
    private Estadio estadio;

public Club(String nombre, Date fechaFundacion, InscripcionTorneo[] inscriptos, Jugador[] jugadores, Estadio estadio) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.inscriptos = new InscripcionTorneo[2];
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
    if (inscriptos.length < 2){
        for (int i = 0; i < inscriptos.length ; i++) {
            if (inscriptos[i] == null){
                inscriptos[i] = it;
            }
            return;
        }
    }
    System.out.println("Máximo de torneos alcanzado");
}
}
