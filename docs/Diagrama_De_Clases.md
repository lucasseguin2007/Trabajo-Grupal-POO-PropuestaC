================================================================================
CLASES Y ATRIBUTOS
================================================================================
personas.Persona (clase abstracta): String nombre, String apellido, int dni, Date fechaNacimiento
personas.Jugador extends personas.Persona: enums.Posicion posicion, double valorMercado, int partidosJugados, int goles, int tarjetasAmarillas, int tarjetasRojas
personas.Entrenador extends personas.Persona: Especialidad especialidad, String licencia
Árbitro extends personas.Persona: Categoria categoria, String colegio
partidos.TernaArbitral:
clubes.Club: String nombre, Date fechaFundacion
torneos.Torneo: String nombre, LocalDate fechaInicio, LocalDate fechaFin, enums.EstadoTorneo estado
torneos.InscripcionTorneo: LocalDate fechaInscripcion
partidos.Estadio: String nombre, int capacidad, String climaHabitual, tipoCesped tipoCesped
clubes.Direccion: String calle, int numero, String ciudad, String provincia
torneos.Plantel:
torneos.JugadorPlantel: int numeroCamiseta, LocalDate fechaAlta, LocalDate fechaBaja
clubes.Contrato: LocalDate fechaInicio, LocalDate fechaFin, sueldo: double
partidos.Partido: LocalDateTime fechahora, enums.EstadoPartido, int golesLocal, golesVisitante int
partidos.NominaPartido: enums.RolEquipo rol
eventos.Evento: int minuto
eventos.Gol extends eventos.Evento: TipoGol tipoGol
eventos.Remate extends eventos.Evento: enums.ZonaRemate zona
eventos.TarjetaAmarilla extends eventos.Evento:
eventos.TarjetaRoja extends eventos.Evento: boolean directa
eventos.Falta extends eventos.Evento:
eventos.Cambio extends eventos.Evento:
eventos.AnulacionEvento: String motivo, LocalDateTime fechaAnulacion
torneos.Sancion: int fechasSuspension, int fechasCumplidas, LocalDate fechaRegistro, enums.MotivoSancion motivo
notificaciones.Suscriptor: String nombre, String correo, String telefono, List<enums.CanalNotificacion>
notificaciones.Notificacion: int minuto, enums.TipoNotificacion tipo, String descripcion, Date fechaEnvio, enums.EstadoEnvio estadoEnvio, enums.CanalNotificacion canal
reportes.GestorReportes:
patrocinios.Sponsor: String nombre, String marca, String logo
patrocinios.Patrocinio: patrocinios.Sponsor sponsor,, double monto, Date fechaInicio, LocalDate fechaFin, boolean estado
reportes.PosicionTorneo: int partidosJugados, int partidosGanados, int partidosEmpatados, int partidosPerdidos, int golesAFavor, int golesEnContra, int puntos
reportes.ProyeccionClasificacion (Interfaz):
reportes.ProyeccionPorPromedio implements reportes.ProyeccionClasificacion: double puntajeCorteHistorico
reportes.ProyeccionPorSimulacionXG implements reportes.ProyeccionClasificacion:

================================================================================
ENUMERACIONES
================================================================================
enums.EstadoTorneo: PLANIFICADO, EN_CURSO, FINALIZADO.
enums.EstadoPartido: PROGRAMADO, EN_JUEGO, FINALIZADO, SUSPENDIDO.
enums.Posicion: ARQUERO, DEFENSOR, MEDIOCAMPISTA, DELANTERO.
enums.EspecialidadEntrenador: DIRECTOR_TECNICO, PREPARADOR_FISICO, AYUDANTE.
enums.CategoriaArbitro: NACIONAL, INTERNACIONAL.
enums.TipoCesped: NATURAL, SINTETICO, HIBRIDO.
enums.ZonaRemate: AREA_CHICA, AREA_GRANDE, FUERA_DEL_AREA, PENAL.
enums.RolEquipo: LOCAL, VISITANTE.
enums.CanalNotificacion: APP_MOVIL, CORREO_PRENSA, SMS.
enums.TipoNotificacion: GOL, TARJETA_ROJA, FINAL_PARTIDO.
enums.EstadoEnvio: PENDIENTE, ENVIADA, FALLIDA.
enums.MotivoSancion: ACUMULACION_AMARILLAS, EXPULSION

================================================================================
RELACIONES
================================================================================
RELACIONES DE HERENCIA
================================================================================
SUPERCLASE -> SUBCLASE

personas.Persona -> personas.Jugador
personas.Persona -> personas.Entrenador
personas.Persona -> Árbitro

eventos.Evento -> eventos.Gol
eventos.Evento -> eventos.Remate
eventos.Evento -> eventos.TarjetaAmarilla
eventos.Evento -> eventos.TarjetaRoja
eventos.Evento -> eventos.Cambio
eventos.Evento -> eventos.Falta
eventos.Evento -> eventos.AnulacionEvento

reportes.ProyeccionClasificacion -> reportes.ProyeccionPorPromedio
reportes.ProyeccionClasificacion -> ProyeccionPorSimulacion

================================================================================
RELACIONES ENTRE TORNEO, CLUBES, JUGADORES Y CONTRATOS
================================================================================
PRIMERA CLASE     | SEGUNDA CLASE     | RELACIÓN   | CARDINALIDAD
torneos.Torneo        | torneos.InscripcionTorneo | Asociación | 1 a N
clubes.Club              | torneos.InscripcionTorneo | Asociación | 1 a N
torneos.InscripcionTorneo | torneos.Plantel       | Composición| 1 a 1
torneos.Plantel | torneos.JugadorPlantel | Composición | 18 a 30
torneos.JugadorPlantel | personas.Jugador | Asociación | 1 a 1
clubes.Club | personas.Jugador | Asociación | 1 a N
personas.Jugador | clubes.Contrato | Asociación | 1 a N
clubes.Club | clubes.Contrato | Asociación | 1 a N
clubes.Club | personas.Entrenador | Agregación | 1 a N

================================================================================
RELACIONES ENTRE PARTIDOS, ESTADIOS Y ARBITRAJE
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDAD
torneos.Torneo | partidos.Partido | Composición | 1 a N
partidos.Partido | clubes.Club | Asociación | 1 a 1
partidos.Partido | partidos.Estadio | Asociación | 1 a 1
clubes.Club | Dirección | Composición | 1 a 1
partidos.Estadio | Dirección | Composición | 1 a 1
partidos.Partido | TemaArbitral | Composición | 1 a 1
partidos.TernaArbitral | Árbitro | Asociación | 1 a 1
partidos.TernaArbitral | Árbitro | Asociación | 1 a 2
Árbitro | clubes.Club (hincha) | Asociación | 1 a N
partidos.Partido | partidos.NominaPartido | Composición | 1 a 2
partidos.NominaPartido | clubes.Club | Asociación | 1 a N
partidos.NominaPartido | personas.Jugador | Asociación | N a M

================================================================================
RELACIONES ENTRE TORNEO, CLUBES, JUGADORES Y CONTRATOS
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
partidos.Partido | eventos.Evento | Composición | 1 a N
personas.Jugador | eventos.Evento | Asociación | 1 a N
eventos.Cambio | personas.Jugador | Asociación | 1 a 1
eventos.AnulacionEvento | eventos.Evento | Asociación | 1 a 1

================================================================================
RELACIÓN DE LAS SANCIONES
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
personas.Jugador | Sanción | Asociación | 1 a N
torneos.Torneo | Sanción | Asociación | 1 a N

================================================================================
RELACIÓN DE PATROCINIOS
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
patrocinios.Sponsor | patrocinios.Patrocinio | Asociación | 1 a N
patrocinios.Patrocinio | clubes.Club | Asociación | 1 a 1
patrocinios.Patrocinio | torneos.Torneo | Asociación | 1 a 1

================================================================================
RELACIÓN DE NOTIFICACIONES
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDADES
notificaciones.Suscriptor | clubes.Club | Asociación | N a M
notificaciones.Suscriptor | Notificación | Asociación | 1 a N
partidos.Partido | Notificación | Asociación | 1 a N

================================================================================
RELACIÓN DE REPORTES Y PROYECCIONES
================================================================================
PRIMERA CLASE | SEGUNDA CLASE | RELACIÓN | CARDINALIDAD
torneos.Torneo | reportes.PosicionTorneo | Asociación | 1 a N
clubes.Club | reportes.PosicionTorneo | Asociación | 1 a N
EstadísticaGoleador | personas.Jugador | Asociación | 1 a 1
reportes.GestorReportes | torneos.Torneo | Dependencia |
reportes.GestorReportes | partidos.Partido | Dependencia |
reportes.GestorReportes | personas.Jugador | Dependencia |
reportes.GestorReportes | clubes.Club | Dependencia |
reportes.GestorReportes | reportes.PosicionTorneo | Dependencia |
ProyeccionClasificación | torneos.Torneo | Dependencia |
reportes.ProyeccionClasificacion | clubes.Club | Dependencia |