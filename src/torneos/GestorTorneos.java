package torneos;

import enums.EstadoTorneo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GestorTorneos implements Serializable{
    private Torneo torneoActual;
    private List<Torneo> historialTorneos;
    public GestorTorneos() {
        this.torneoActual = null;
        this.historialTorneos = new ArrayList<>();
    }

    public List<Torneo> getHistorialTorneos() {
        return historialTorneos;
    }

    public Torneo getTorneoActual() {
        return torneoActual;
    }
    public boolean iniciarNuevoTorneo(Torneo torneo) {
        if (torneo == null || torneoActual != null) {
            return false;
        }
        if (torneo.getEstado() != EstadoTorneo.PLANIFICADO) {
            return false;
        }
        torneoActual = torneo;
        return true;
    }
    public void cargarDatos(GestorTorneos gestorCargado) {
        if (gestorCargado == null) {
            throw new IllegalArgumentException("El gestor cargado no puede ser nulo");
        }
        this.torneoActual = gestorCargado.getTorneoActual();
        this.historialTorneos = new ArrayList<>(gestorCargado.getHistorialTorneos());
    }

    public boolean archivarTorneoActual() {
        if (torneoActual == null) {
            return false;
        }
        if (torneoActual.getEstado() != EstadoTorneo.FINALIZADO) {
            return false;
        }
        historialTorneos.add(torneoActual);
        torneoActual = null;
        return true;
    }
}
