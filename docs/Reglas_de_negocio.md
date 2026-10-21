===============================================================================
REGLAS DE NEGOCIO
===============================================================================

Torneos y estadios
------------------
* Un torneo tiene nombre, fecha de inicio, fecha de fin y un estado: planificado, en curso o finalizado.
* Solo se puede pasar de un estado al siguiente, y un torneo no puede iniciarse con menos de 4 clubes inscriptos.
* Un torneo en curso no admite nuevas inscripciones.
* Las fechas de los partidos deben estar dentro del período del torneo.
* Un estadio tiene un nombre, una capacidad, una dirección (calle, número, ciudad y provincia) y una lista de condiciones habituales (clima o tipo de césped).
* Un estadio no puede alojar dos partidos con menos de 3 horas de diferencia.

Clubes, plantel y personas
--------------------------
* Un club tiene nombre, fecha de fundación y sede social, y puede participar en varios torneos a lo largo del tiempo, pero una sola vez en cada uno.
* El plantel de un club en un torneo tiene entre 18 y 30 jugadores.
* Un jugador solo puede pertenecer a un club por vez en un mismo torneo.
* Todos los participantes (jugadores, entrenadores y árbitros) son personas con DNI, nombre, apellido y fecha de nacimiento.
* Los datos y las estadísticas de un jugador se conservan aunque rescinda contrato o quede libre. Cada paso por un club queda registrado con su fecha de inicio y de fin.
* Los árbitros tienen una categoría (nacional o internacional) y un colegio al que pertenecen.
* Los partidos de torneos internacionales exigen una terna de categoría internacional.
* Un árbitro no puede dirigir a un club del que sea hincha declarado ni tener otro partido el mismo día.

Partidos y eventos
------------------
* Un partido enfrenta a dos clubes distintos (local y visitante) en una fecha, en un estadio y con una terna de tres árbitros: un principal y dos asistentes.
* Estados del partido: programado, en juego, finalizado y suspendido.
* Solo se pueden registrar eventos en un partido en juego.
* Un club no puede jugar dos partidos con menos de 48 horas de diferencia.
* Tipos de evento: gol, remate, tarjeta amarilla, tarjeta roja, cambio y falta. Todo evento tiene un minuto (de 0 a 90, más el adicional hasta 120), el partido y el jugador involucrado.
* Un jugador involucrado en un evento debe estar en la nómina del club en ese partido.
* Un jugador sustituido no puede volver a jugar ese partido.
* Un gol puede ser a favor, en contra o de penal, y suma al marcador del club que corresponda. Un gol en contra suma al rival.
* Dos tarjetas amarillas en un mismo partido equivalen a una roja.
* Un jugador expulsado no puede ser sustituido, y el club queda con un jugador menos.
* Cada club puede hacer hasta 5 cambios por partido.
* Un evento registrado no se elimina: si hubo un error, el operador lo corrige con un evento de anulación que queda asociado al original.

Sanciones
---------
* Un jugador que acumula 5 amarillas en el torneo queda suspendido para la fecha siguiente.
* Una roja directa implica 1 fecha de suspensión, y el sistema no permite incluirlo en la nómina del partido siguiente.

Notificaciones
--------------
* Se notifica a los suscriptores cuando hay un gol, una tarjeta roja o el final del partido. Cada notificación indica el partido, el minuto y el hecho.
* Los canales iniciales son app móvil, correo para prensa y SMS. Cada suscriptor elige qué canales usa y qué clubes sigue.
* Si un canal falla, el sistema lo registra y sigue con los demás: un fallo en el envío no puede frenar la carga del partido.
* Debe poder incorporarse un canal nuevo (por ejemplo, mensería instantánea) sin modificar la lógica de los partidos.

Patrocinios
-----------
* Un sponsor tiene nombre, marca y logo, y puede patrocinar a varios clubes y torneos al mismo tiempo.
* Cada patrocinio tiene un monto, una fecha de inicio y una de fin, y solo puede estar vigente uno por sponsor y destinatario.
* Un club no puede tener más de un sponsor principal vigente a la vez.

Tabla de posiciones y reportes
------------------------------
* Victoria: 3 puntos. Empate: 1 punto. Derrota: 0 puntos. Solo cuentan los partidos finalizados.
* Desempate, en este orden: diferencia de gol, goles a favor, resultado entre los clubes empatados.
* El goleador es el jugador con más goles a favor (los de penal cuentan, los goles en contra no).
* La valla menos vencida es el club con menor promedio de goles recibidos por partido.

Proyecciones
------------
* xG de un remate: depende de la zona desde la que se patea: área chica 0,40, área grande 0,15, fuera del área 0,04, penal 0,76. El xG de un jugador o un club en un partido es la suma de los xG de sus remates.
* Probabilidad de clasificación: el sistema debe ofrecer al menos dos métodos de cálculo intercambiables.
    - El primero proyecta los puntos finales a partir del promedio de puntos por partido y los compara con el puntaje de corte de años anteriores.
    - El segundo simula los partidos restantes usando el xG promedio de cada club.
* Debe poder sumarse un método de proyección nuevo sin modificar los reportes que lo usan.
