package reportes;

import clubes.Club;
import torneos.Torneo;

public interface ProyeccionClasificacion {
    double calcularProbabilidad(Torneo torneo, Club club);
}
