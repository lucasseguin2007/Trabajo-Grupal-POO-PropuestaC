import Enums.CanalNotificacion;

import java.util.ArrayList;
import java.util.List;

public class Suscriptor {
    private String nombre;
    private String correo;
    private String telefono;
    private List<CanalNotificacion> canales;
    private List<Club> clubes;

    public Suscriptor(String nombre, String correo, String telefono, List<CanalNotificacion> canalNotificaciones) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.canales = new ArrayList<>();
        this.clubes = new ArrayList<>();
    }



    public void seguirClub(Club club){
        if (club == null) {
            throw new IllegalArgumentException("El club no existe");
        }
        if (!clubes.contains(club)) {
            clubes.add(club);
        }
    }

    public void dejarDeSeguir(Club club){
        if (club == null) {
            throw new IllegalArgumentException("El club no existe");
        }
        clubes.remove(club);
    }

    public void configurarCanal(CanalNotificacion canal){
        if (canal == null) {
            throw new IllegalArgumentException("El canal no existe");
        }
        switch (canal){
            case SMS:
                if (telefono == null) {
                    throw new IllegalStateException("No se puede configurar SMS sin teléfono");
                }

            case CORREO_PRENSA:
                if (correo == null || correo.contains(" ")) {
                    throw new IllegalStateException("No se puede configurar correo sin una dirección");
                }
                break;

            case APP_MOVIL:
                break;
        }
        if (!canales.contains(canal)){
            canales.add(canal);
        }


    }




}
