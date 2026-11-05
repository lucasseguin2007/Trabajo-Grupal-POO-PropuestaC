import Enums.EstadoTorneo;

import java.time.LocalDate;

public class Torneo {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoTorneo estado;
    private InscripcionTorneo inscriptos[];

    public Torneo(String nombre, LocalDate fechaInicio, LocalDate fechaFin, EstadoTorneo estado) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.inscriptos = new InscripcionTorneo[30];
    }
}
