package personas;

import clubes.Club;
import enums.CategoriaArbitro;

import java.time.LocalDate;

public class Arbitro extends Persona {
    private CategoriaArbitro categoria;
    private String colegio;
    private Club clubHincha;

    public Arbitro(int dni, String nombre, String apellido, LocalDate fechaNacimiento, CategoriaArbitro categoria, Club clubHincha, String colegio) {
        super(dni, nombre, apellido, fechaNacimiento);
        this.categoria = categoria;
        this.clubHincha = clubHincha;
        this.colegio = colegio;
    }

    public CategoriaArbitro getCategoria() {
        return categoria;
    }

    public Club getClubHincha() {
        return clubHincha;
    }

    public String getColegio() {
        return colegio;
    }

    @Override
    public String obtenerDescripcion() {
        return "Personas.Arbitro: " + getNombre() + " " + getApellido()
                + " | Categoria: " + categoria
                + " | Colegio: " + colegio
                + " | Clubes.Club Hincha: " + clubHincha;
    }


}
