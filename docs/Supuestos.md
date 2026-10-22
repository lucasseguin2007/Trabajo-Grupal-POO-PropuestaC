==================================================================
SUPUESTOS
==================================================================
* Cada torneo, club, estadio, partido, contratos y patrocinio contendrá un identificador único para facilitar su acceso y gestión.
* Se diferencian los torneos nacionales e internacionales mediante una clasificación explícita.
* Se permite que el plantel esté incompleto, pero para confirmar la inscripción de un club deberá contar con entre 18 y 30 jugadores.
* La generación automática de fixture utilizará una estructura todos contra todos de una sola pasada, alternando localías.
* El minuto registrado tiene como límite hasta 120 para contemplar el tiempo adicional si es necesario.
* No se permitirá asignar dos veces el mismo árbitro dentro de una terna ni asignarlo a otro partido en la misma fecha.
* El operador es un usuario como un quinto árbitro autorizado para registrar y corregir eventos del partido.
* Cada evento podrá tener como máximo una anulación efectiva. Tanto los registros originales y sus anulaciones permanecen almacenados para preservar la trazabilidad.
* Dos patrocinios se consideran simultáneamente vigentes cuando sus períodos de vigencia se superpongan.
* Las tablas y los reportes se calcularán utilizando información válida de partidos finalizados, sin considerar eventos anulados.
* Si dos clubes empatan en todos los criterios establecidos, compartirán la posición hasta que se defina un criterio adicional.
* La falla de un canal no impide continuar con los restantes.