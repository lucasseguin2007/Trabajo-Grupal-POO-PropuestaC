================================================================================
CASO DE USO: CU-01 - Administrar clubes
================================================================================
Objetivo: Poder modificar atributos de los clubes
Actor principal: Organizador

Precondiciones:
1. Debe haber al menos un club inscripto

Flujo principal:
Paso 1: El organizador ingresa a administrar clubes
Paso 2: El organizador selecciona el club que quiere modificar
Paso 3: El organizador selecciona el dato que desea modificar del club
Paso 4: El sistema solicita al usuario el dato a modificar
Paso 5: El organizador carga el dato
Paso 6: El sistema consulta si desea registrar el cambio
Paso 7: El organizador confirma
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 7):
- El organizador solicita no registrar los cambios
- El sistema ofrece corregir los datos
- El organizador corrige los datos
- El sistema registra los cambios

Notas: -


================================================================================
CASO DE USO: CU-02 - Administrar arbitros
================================================================================
Objetivo: Poder modificar los atributos de cada arbitro
Actor principal: Organizador

Precondiciones:
1. Debe haber al menos un árbitro inscripto

Flujo principal:
Paso 1: El organizador ingresa a modificar arbitro
Paso 2: El organizador selecciona el arbitro que desea modificar
Paso 3: El organizador selecciona el atributo que desea modificar
Paso 4: El sistema solicita el nuevo valor para el atributo
Paso 5: El organizador carga el nuevo valor
Paso 6: El sistema solicita una confirmación para registrar el cambio
Paso 7: El organizador confirma
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 7):
- El organizador deniega
- El sistema ofrece corregir los datos
- El organizador corrige los datos
- El sistema registra los cambios

Notas: -

================================================================================
CASO DE USO: CU-03 - Administrar torneo
================================================================================
Objetivo: Poder modificar los atributos de cada torneo
Actor principal: Organizador

Precondiciones:
1. El torneo no debe haber comenzado

Flujo principal:
Paso 1: El organizador ingresa a modificar torneo
Paso 2: El organizador selecciona el torneo que desea modificar
Paso 3: El organizador selecciona el atributo que desea modificar
Paso 4: El sistema solicita el nuevo valor para el atributo
Paso 5: El organizador carga el nuevo valor
Paso 6: El sistema solicita una confirmación para registrar el cambio
Paso 7: El organizador confirma
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 7):
- El organizador deniega
- El sistema ofrece corregir los datos
- El organizador corrige los datos
- El sistema registra los cambios

Notas: -

================================================================================
CASO DE USO: CU-04 - Administrar estadio
================================================================================
Objetivo: Poder modificar los atributos de cada estadio
Actor principal: Organizador

Precondiciones:
(Ninguna)

Flujo principal:
Paso 1: El organizador ingresa a modificar estadio
Paso 2: El organizador selecciona el estadio que desea modificar
Paso 3: El organizador selecciona el atributo que desea modificar
Paso 4: El sistema solicita el nuevo valor para el atributo
Paso 5: El organizador carga el nuevo valor
Paso 6: El sistema solicita una confirmación para registrar el cambio
Paso 7: El organizador confirma
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 7):
- El organizador deniega
- El sistema ofrece corregir los datos
- El organizador corrige los datos
- El sistema registra los cambios

Notas: -

================================================================================
CASO DE USO: CU-05 - Generar fixture
================================================================================
Objetivo: Que el sistema brinde cada enfrentamiento que tendrá cada club, cada fecha
Actor principal: Organizador

Precondiciones:
1. El torneo no debe haber comenzado

Flujo principal:
Paso 1: El organizador ingresa a generar fixture
Paso 2: El organizador selecciona el torneo al que desea generar el fixture
Paso 3: El organizador solicita al sistema que genere el fixture del torneo
Paso 4: El sistema genera el fixture

Flujos Alternativos:
(Ninguno)

Notas:
- Solo se puede utilizar una vez por torneo.

================================================================================
CASO DE USO: CU-06 - Registrar club
================================================================================
Objetivo: Poder registrar un nuevo club en el sistema
Actor principal: Organizador

Precondiciones:
(Ninguna)

Flujo principal:
Paso 1: El organizador ingresa a registrar club
Paso 2: El sistema solicita los datos del nuevo club a registrar
Paso 3: El organizador carga los datos
Paso 4: El sistema verifica que el nombre del club no se encuentre en uso
Paso 5: El sistema solicita una confirmación
Paso 6: El organizador confirma los datos
Paso 7: El sistema registra el nuevo club

Flujos Alternativos:
Alt 1 (Paso 4):
- El sistema notifica que el nombre solicitado no se encuentra disponible
- El sistema solicita un nuevo nombre
- El usuario ingresa el nuevo nombre
Alt 2 (Paso 6):
- El organizador deniega los datos
- El sistema cancela la agregación

Notas:
- El club se registra fuera de los torneos y se agrega luego cada uno a estos. El club no depende de los torneos para existir.

================================================================================
CASO DE USO: CU-07 - Administrar jugadores
================================================================================
Objetivo: Poder modificar los atributos de cada jugador
Actor principal: Organizador

Precondiciones:
(Ninguna)

Flujo principal:
Paso 1: El organizador ingresa a modificar jugador
Paso 2: El organizador selecciona el jugador que desea modificar
Paso 3: El organizador selecciona el atributo que desea modificar
Paso 4: El sistema solicita el nuevo valor para el atributo
Paso 5: El organizador carga el nuevo valor
Paso 6: El sistema solicita una confirmación para registrar el cambio
Paso 7: El organizador confirma
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 7):
- El organizador deniega
- El sistema ofrece corregir los datos
- El organizador corrige los datos
- El sistema registra los cambios

Notas:
(Vacío)

================================================================================
CASO DE USO: CU-08 - Administrar entrenador
================================================================================
Objetivo: Poder modificar los atributos de cada entrenador
Actor principal: Organizador

Precondiciones:
(Ninguna)

Flujo principal:
Paso 1: El organizador ingresa a modificar entrenador
Paso 2: El organizador selecciona el entrenador que desea modificar
Paso 3: El organizador selecciona el atributo que desea modificar
Paso 4: El sistema solicita el nuevo valor para el atributo
Paso 5: El organizador carga el nuevo valor
Paso 6: El sistema solicita una confirmación para registrar el cambio
Paso 7: El organizador confirma
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 7):
- El organizador deniega
- El sistema ofrece corregir los datos
- El organizador corrige los datos
- El sistema registra los cambios

Notas: -

================================================================================
CASO DE USO: CU-09 - Registrar evento
Objetivo: Almacenar datos de cada partido
Actor principal: Árbitro

Precondiciones:
1. El partido debe estar en estado EN_JUEGO, con el jugador habilitado y convocado.

Flujo principal:
Paso 1: El Árbitro ingresa ID del evento
Paso 2: El sistema pide los datos
Paso 3: El Árbitro ingresa datos
Paso 4: El sistema chequea que sean válidos
Paso 5: El sistema pregunta si es correcto lo ingresado
Paso 6: El Árbitro acepta
Paso 7: El sistema busca evento
Paso 8: El sistema carga datos

Flujos Alternativos:
Alt 1 (Paso 5):
- El sistema pregunta si es correcto lo ingresado
- El Árbitro deniega
- El sistema pide datos
- Continúa en el Paso 3
CASO DE USO: CU-10 - Iniciar torneo
Objetivo: Permitirle al organizador dar por comenzado el torneo
Actor principal: Organizador

Precondiciones:
1. Debe haber al menos 4 clubes inscriptos

Flujo principal:
Paso 1: El organizador solicita iniciar el torneo
Paso 2: El sistema verifica que los clubes se encuentren completos
Paso 3: El sistema consulta si desea que cree el fixture del torneo
Paso 4: El organizador confirma
Paso 5: El sistema llama a “Generar fixture”

Flujos Alternativos:
Alt 1 (Paso 4):
- El organizador solicita no generar el fixture
- El sistema únicamente inicia el torneo

Notas: Al iniciar el torneo el sistema bloquea la posibilidad de modificar cosas de los clubes como modificar a los jugadores. (Refiriéndose únicamente a los registrados para ese torneo)

CASO DE USO: CU-11 - Calcular proyecciones
Objetivo: Poder realizar pruebas de los posibles resultados que podrían darse en el torneo
Actor principal: Analista estadístico

Precondiciones:
(Ninguna)

Flujo principal:
Paso 1: El analista ingresa a calcular proyecciones
Paso 2: El sistema consulta el torneo y el club que desea consultar
Paso 3: El analista ingresa ambos datos
Paso 4: El sistema consulta que tipo de proyección desea consultar
Paso 5: El analista selecciona la proyección por historial
Paso 6: El sistema llama a “Calcular por historial”

Flujos Alternativos:
Alt 1 (Paso 4):
- El analista selecciona la proyección por rendimiento
- El sistema llama a “Calcular por rendimiento”

Notas: -

CASO DE USO: CU-17 - Registrar tarjetas
Objetivo: Tener registrado las tarjetas de cada partido
Actor principal: Árbitro

Precondiciones:
1. Partido empezado

Flujo principal:
Paso 1: El Árbitro registra un evento
Paso 2: El Árbitro selecciona la opción amonestación y la tarjeta
Paso 3: El Árbitro ingresa nombre del jugador
Paso 4: El sistema registra la sanción

Flujos Alternativos:
Alt 1 (Paso 4):
- El sistema detecta que ya tiene amarilla ese jugador
- El sistema notifica al árbitro que es la segunda amarilla
- Continúa en el Paso 1
CASO DE USO: CU-11 - Calcular proyecciones
Objetivo: Poder realizar pruebas de los posibles resultados que podrían darse en el torneo
Actor principal: Analista estadístico

Precondiciones:
(Ninguna)

Flujo principal:
Paso 1: El analista ingresa a calcular proyecciones
Paso 2: El sistema consulta el torneo y el club que desea consultar
Paso 3: El analista ingresa ambos datos
Paso 4: El sistema consulta que tipo de proyección desea consultar
Paso 5: El analista selecciona la proyección por historial
Paso 6: El sistema llama a “Calcular por historial”

Flujos Alternativos:
Alt 1 (Paso 4):
- El analista selecciona la proyección por rendimiento
- El sistema llama a “Calcular por rendimiento”

Notas: -

CASO DE USO: CU-14 - Consultar tabla
Objetivo: Recibir la tabla de posiciones de un torneo específico que muestre: clubes, rankings, puntajes, goles, diferencia de gol, partidos jugados.
Actor principal: Abstracto (Analista estadístico, suscriptor)

Precondiciones:
1. El torneo debe haber iniciado

Flujo principal:
Paso 1: El usuario ingresa a “Consultar tabla”
Paso 2: El sistema consulta que torneo desea consultar
Paso 3: El usuario selecciona el torneo que desea
Paso 4: El sistema presenta la tabla solicitada

Flujos Alternativos:
Alt 1 (Paso 4):
- El sistema detecta que el torneo no ha iniciado
- El sistema notifica que la tabla aún no puede visualizarse porque el torneo no ha comenzado

Notas: Hay dos posibles actores principales, el analista y el suscriptor. Por lo que es un actor abstracto

CASO DE USO: CU-15 - Consultar fixture
Objetivo: Presentarle al suscriptor el fixture completo de un torneo
Actor principal: Suscriptor

Precondiciones:
1. El torneo debe haber iniciado
2. El fixture debe haber sido generado

Flujo principal:
Paso 1: El suscriptor ingresa a consultar fixture
Paso 2: El sistema consulta que torneo desea consultar
Paso 3: El suscriptor selecciona el torneo
Paso 4: El sistema presenta el fixture completo

Flujos Alternativos:
Alt 1 (Paso 3):
- El sistema detecta que el torneo no ha iniciado
- El sistema notifica al suscriptor que el fixture no se encuentra disponible aún.
  Alt 2 (Paso 4):
- El sistema detecta que el fixture no ha sido generado
- El sistema notifica al suscriptor que el fixture no se encuentra disponible aún.

Notas: -

================================================================================
CASO DE USO: CU-18 - Registrar cambios
Objetivo: Tener registrado los cambios del partido
Actor principal: Árbitro

Precondiciones:
1. Partido empezado

Flujo principal:
Paso 1: El Árbitro registra un evento
Paso 2: El Árbitro selecciona la opción cambio
Paso 3: El Árbitro ingresa nombre del jugador que entra y del que sale y el minuto
Paso 4: El sistema registra el cambio

Flujos Alternativos:
Alt 1:
-
CASO DE USO: CU-16 - Consultar notificaciones
Objetivo: Presentarle a el suscriptor todas las notificaciones que tiene sin leer
Actor principal: Suscriptor

Precondiciones:
1. El suscriptor debe tener suscripciones activas

Flujo principal:
Paso 1: El suscriptor ingresa a la pestaña de notificaciones
Paso 2: El sistema revisa las notificaciones sin abrir que tiene el suscriptor
Paso 3: El sistema presenta las notificaciones sin leer

Flujos Alternativos:
Alt 1 (Paso 2):
- El sistema detecta que no hay notificaciones nuevas
- El sistema avisa al suscriptor que no tiene notificaciones pendientes

Notas: -
