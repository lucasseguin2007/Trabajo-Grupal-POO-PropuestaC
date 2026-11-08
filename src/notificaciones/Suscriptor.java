package notificaciones;

import clubes.Club;
import enums.CanalNotificacion;

import java.util.ArrayList;
import java.util.List;

public class Suscriptor {
    private String nombre;
    private String correo;
    private String telefono;
    private List<CanalEnvio> canales;
    private List<Club> clubes;

    public Suscriptor(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.canales = new ArrayList<>();
        this.clubes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public List<CanalEnvio> getCanales() {
        return new ArrayList<>(canales);
    }

    public List<Club> getClubes() {
        return new ArrayList<>(clubes);
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

    public boolean configurarCanal(CanalEnvio canal){
        if (canal == null) {
            return false;
        }
        for (CanalEnvio existente : canales) {
            if (existente.getClass() == canal.getClass()) {
                return false;
            }
        }
        canales.add(canal);
        return true;

    }

}
