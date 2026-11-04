import java.time.LocalDate;

public abstract class Persona  {
    private int dni;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    //Metodo Constructor
    public Persona(int dni, String nombre, String apellido, LocalDate fechaNacimiento) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    //Getters
    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    //Metodos
    public abstract String obtenerDescripcion();
}
