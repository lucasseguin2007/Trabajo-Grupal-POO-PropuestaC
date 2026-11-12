package personas;

import enums.EspecialidadEntrenador;

import java.time.LocalDate;

public class Entrenador extends Persona {
    private EspecialidadEntrenador especialidad;
    private String licencia;

    public Entrenador(int dni, String nombre, String apellido, LocalDate fechaNacimiento, EspecialidadEntrenador especialidad, String licencia) {
        super(dni, nombre, apellido, fechaNacimiento);
        this.especialidad = especialidad;
        this.licencia = licencia;
    }

    public EspecialidadEntrenador getEspecialidad() {
        return especialidad;
    }

    public String getLicencia() {
        return licencia;
    }

    @Override
    public String obtenerDescripcion() {
        return "Personas.Entrenador: " + getNombre() + " " + getApellido()
                + " | Especialidad: " + especialidad
                + " | Licencia: " + licencia;
    }

}
