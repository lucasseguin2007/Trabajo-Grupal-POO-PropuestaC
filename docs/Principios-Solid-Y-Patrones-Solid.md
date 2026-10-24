================================================================================
PRINCIPIOS SOLID
================================================================================
1. SRP — PRINCIPIO DE RESPONSABILIDAD ÚNICA

Problema: En el diagrama inicial, la notificación tiene los datos de un aviso y también el método enviar(): boolean. Lo cual combina la representación de los datos del evento deportivo con la responsabilidad de enviarlos a diferentes canales. Por ejemplo, un cambio en el formato del mensaje y un cambio en el canal con SMS afectarían a la misma clase, aunque responden a responsabilidades diferentes.
Solución: Una gran solución sería separar dicha responsabilidades, dejando los datos de la notificación separados de la coordinación de su envío mediante ServicioNotificaciones.
Clases afectadas: Notificación y ServicioNotificaciones
Ventaja: Los cambios que se hagan en el mecanismo de envío no obligan a modificar la clase que representa la notificación mejorando la cohesión del código.

2. OCP — PRINCIPIO ABIERTO/CERRADO

Problema: Al querer agregar un canal al sistema inicial implicaría modificar las estructuras de condición que son los responsables de seleccionar el mecanismo de envío.
Solución: Introducir una interfaz llamada CanalEnvio e implementar cada canal mediante una clase concreta.
Clases Afectadas: CanalEnvio, CanalApp, CanalCorreo, CanalSMS y futuras implementaciones que vayan surgiendo.
Ventaja: pueden incorporarse nuevos canales de comunicaciones sin modificar la lógica del envío.

3. DIP — PRINCIPIO DE INVERSIÓN DE DEPENDENCIAS

Problema: El envío de notificaciones podría depender directamente de implementaciones concretas de servicios externos.
Solución: Hacer que ServicioNotificaciones dependa de la abstracción CanalEnvio y recibir las implementaciones mediante el constructor.
Clases afectadas: ServicioNotificaciones, CanalEnvio y las implementaciones concretas.
Ventajas: permite que el servicio pueda funcionar con diferentes mecanismos de envío, que puede reemplazarse o probarse independientemente.
================================================================================
PATRONES GRASP
================================================================================
1. EXPERTO EN INFORMACIÓN

Este patrón se puede aplicar en cálculo del xG, ya que Remate conoce la zona desde la que fue realizado entonces es lógico que el objeto lo calcule.
Justificación: No tiene sentido que GestorReportes conozca los valores específicos de cada zona si puede pedirle a cada remate su resultado.
Clases involucradas: Remate, ZonaRemate y GestorReportes.
Ventaja: la lógica de cálculo queda cerca de sus datos y las otras clases solo tiene que invoca calcularXG().

2. CREADOR

Partido mantiene historia de los eventos que ocurren mediante composición entonces también puede ser un candidato para crear eventos anulaciones. Permite al operador solicitar anular un gol si se identifica el evento,se verifica que pertenezca al historial de Partido, que sea anulable y que todavía no tenga una anulación válida.
Justificación: Partido contiene los eventos y dispone de la información necesaria para registrar la corrección.
Clases involucradas: Partido, Evento, AnulaciónEvento
Ventaja: La creación del registro de anulación se encuentra en el objeto que contiene el historial lo cual preserva la trazabilidad.

3. CONTROLADOR

Implementar una clase ControladorPartidos
Justificación: ControladorPartidos recibe las solicitudes del operador y coordina los objetos del dominio involucrado en la operación.
Clases involucradas: ControladorPartidos, Partido y ServicioNotificaciones.
Ventaja: la coordinación queda separada de las reglas de negocio, que siguen siendo responsabilidad de las clases del dominio.

4. BAJO ACOPLAMIENTO

Cada vez que se cambiaría un canal, podría terminar modificando el código relacionado con el partido. Una solución podría ser mantener responsabilidades distribuidas. Partido registra y valida eventos, ControladorPartidos coordina el caso de uso, ServicioNotificaciones gestión el envío y CanalEnvio define el contrato para las implementaciones.
Justificación: Evita que Partido dependa de implementaciones externas de notificación.
Clases involucradas: Partido, ControladorPartidos, ServicioNotificaciones, CanalEnvio y sus implementaciones.
Ventaja: los cambios en los canales de envío tienen un impacto limitado sobre la lógica deportiva.
