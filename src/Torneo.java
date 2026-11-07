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

    public boolean iniciarTorneo(){
        if ((estado == EstadoTorneo.EN_CURSO)||(estado == EstadoTorneo.FINALIZADO)){
            return false;
        }
        estado = EstadoTorneo.EN_CURSO;
        return true;
    }

    public void finalizarTorneo(){
        if (estado == EstadoTorneo.EN_CURSO){
            estado = EstadoTorneo.FINALIZADO;
        }
    }

    public List<Partido> generarFixture(){
        for (int i = 0; i < inscriptos.length; i++) {
            for (int j = 0; j < ; j++) {
                inscriptos[i].getC().getNombre()
            }
        }
    }

}
