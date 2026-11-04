================================================================================
CLASES Y ATRIBUTOS
================================================================================
Persona (clase abstracta): String nombre, String apellido, int dni, Date fechaNacimiento
Jugador extends Persona: Posicion posicion, double valorMercado, int partidosJugados, int goles, int tarjetasAmarillas, int tarjetasRojas
Entrenador extends Persona: Especialidad especialidad, String licencia
Árbitro extends Persona: Categoria categoria, String colegio
TernaArbitral:
Club: String nombre, Date fechaFundacion
Torneo: String nombre, LocalDate fechaInicio, LocalDate fechaFin, EstadoTorneo estado
InscripcionTorneo: LocalDate fechaInscripcion
Estadio: String nombre, int capacidad, String climaHabitual, tipoCesped tipoCesped
Direccion: String calle, int numero, String ciudad, String provincia
Plantel:
JugadorPlantel: int numeroCamiseta, LocalDate fechaAlta, LocalDate fechaBaja
Contrato: LocalDate fechaInicio, LocalDate fechaFin, sueldo: double
Partido: LocalDateTime fechahora, EstadoPartido, int golesLocal, golesVisitante int
NominaPartido: RolEquipo rol
Evento: int minuto
Gol extends Evento: TipoGol tipoGol
Remate extends Evento: ZonaRemate zona
TarjetaAmarilla extends Evento:
TarjetaRoja extends Evento: boolean directa
Falta extends Evento:
Cambio extends Evento:
AnulacionEvento: String motivo, LocalDateTime fechaAnulacion
Sancion: int fechasSuspension, int fechasCumplidas, LocalDate fechaRegistro, MotivoSancion motivo
Suscriptor: String nombre, String correo, String telefono, List<CanalNotificacion>
Notificacion: int minuto, TipoNotificacion tipo, String descripcion, Date fechaEnvio, EstadoEnvio estadoEnvio, CanalNotificacion canal
GestorReportes:
Sponsor: String nombre, String marca, String logo
Patrocinio: Sponsor sponsor,, double monto, Date fechaInicio, LocalDate fechaFin, boolean estado
PosicionTorneo: int partidosJugados, int partidosGanados, int partidosEmpatados, int partidosPerdidos, int golesAFavor, int golesEnContra, int puntos
ProyeccionClasificacion (Interfaz):
ProyeccionPorPromedio implements ProyeccionClasificacion: double puntajeCorteHistorico
ProyeccionPorSimulacionXG implements ProyeccionClasificacion:

================================================================================
ENUMERACIONES
================================================================================
EstadoTorneo: PLANIFICADO, EN_CURSO, FINALIZADO.
EstadoPartido: PROGRAMADO, EN_JUEGO, FINALIZADO, SUSPENDIDO.
Posicion: ARQUERO, DEFENSOR, MEDIOCAMPISTA, DELANTERO.
EspecialidadEntrenador: DIRECTOR_TECNICO, PREPARADOR_FISICO, AYUDANTE.
CategoriaArbitro: NACIONAL, INTERNACIONAL.
TipoCesped: NATURAL, SINTETICO, HIBRIDO.
ZonaRemate: AREA_CHICA, AREA_GRANDE, FUERA_DEL_AREA, PENAL.
RolEquipo: LOCAL, VISITANTE.
CanalNotificacion: APP_MOVIL, CORREO_PRENSA, SMS.
TipoNotificacion: GOL, TARJETA_ROJA, FINAL_PARTIDO.
EstadoEnvio: PENDIENTE, ENVIADA, FALLIDA.
MotivoSancion: ACUMULACION_AMARILLAS, EXPULSION

================================================================================
RELACIONES
================================================================================
RELACIONES DE HERENCIA
================================================================================
SUPERCLASE -> SUBCLASE

Persona -> Jugador
Persona -> Entrenador
Persona -> Árbitro

Evento -> Gol
Evento -> Remate
Evento -> TarjetaAmarilla
Evento -> TarjetaRoja
Evento -> Cambio
Evento -> Falta
Evento -> AnulacionEvento

ProyeccionClasificacion -> ProyeccionPorPromedio
ProyeccionClasificacion -> ProyeccionPorSimulacion

================================================================================
RELACIONES ENTRE TORNEO, CLUBES, JUGADORES Y CONTRATOS
================================================================================
PRIMERA CLASE     | SEGUNDA CLASE     | RELACIÓN   | CARDINALIDAD
Torneo        | InscripcionTorneo | Asociación | 1 a N
Club              | InscripcionTorneo | Asociación | 1 a N
InscripcionTorneo | Plantel       | Composición| 1 a 1
Plantel | JugadorPlantel | Composición | 18 a 30
JugadorPlantel | Jugador | Asociación | 1 a 1
Club | Jugador | Asociación | 1 a N
Jugador | Contrato | Asociación | 1 a N
Club | Contrato | Asociación | 1 a N
Club | Entrenador | Agregación | 1 a N

================================================================================
RELACIONES ENTRE PARTIDOS, ESTADIOS Y ARBITRAJE
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDAD
Torneo | Partido | Composición | 1 a N
Partido | Club | Asociación | 1 a 1
Partido | Estadio | Asociación | 1 a 1
Club | Dirección | Composición | 1 a 1
Estadio | Dirección | Composición | 1 a 1
Partido | TemaArbitral | Composición | 1 a 1
TernaArbitral | Árbitro | Asociación | 1 a 1
TernaArbitral | Árbitro | Asociación | 1 a 2
Árbitro | Club (hincha) | Asociación | 1 a N
Partido | NominaPartido | Composición | 1 a 2
NominaPartido | Club | Asociación | 1 a N
NominaPartido | Jugador | Asociación | N a M

================================================================================
RELACIONES ENTRE TORNEO, CLUBES, JUGADORES Y CONTRATOS
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
Partido | Evento | Composición | 1 a N
Jugador | Evento | Asociación | 1 a N
Cambio | Jugador | Asociación | 1 a 1
AnulacionEvento | Evento | Asociación | 1 a 1

================================================================================
RELACIÓN DE LAS SANCIONES
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
Jugador | Sanción | Asociación | 1 a N
Torneo | Sanción | Asociación | 1 a N

================================================================================
RELACIÓN DE PATROCINIOS
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
Sponsor | Patrocinio | Asociación | 1 a N
Patrocinio | Club | Asociación | 1 a 1
Patrocinio | Torneo | Asociación | 1 a 1

================================================================================
RELACIÓN DE NOTIFICACIONES
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
Suscriptor | Club | Asociación | N a M
Suscriptor | Notificación | Asociación | 1 a N
Partido | Notificación | Asociación | 1 a N

================================================================================
RELACIÓN DE REPORTES Y PROYECCIONES
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDAD
Torneo | PosicionTorneo | Asociación | 1 a N
Club | PosicionTorneo | Asociación | 1 a N
EstadísticaGoleador | Jugador | Asociación | 1 a 1
GestorReportes | Torneo | Dependencia |
GestorReportes | Partido | Dependencia |
GestorReportes | Jugador | Dependencia |
GestorReportes | Club | Dependencia |
GestorReportes | PosicionTorneo | Dependencia |
ProyeccionClasificación | Torneo | Dependencia |
ProyeccionClasificacion | Club | Dependencia |