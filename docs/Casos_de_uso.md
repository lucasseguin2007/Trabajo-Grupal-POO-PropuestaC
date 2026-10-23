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
Alt 1 (Paso 1):
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
Alt 1 (Paso 1):
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
Paso 7: El organizador confirms
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 1):
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
Alt 1 (Paso 1):
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
CASO DE USO: CU-06 - Inscribir club
================================================================================
Objetivo: Poder registrar un nuevo club en el sistema
Actor principal: Organizador

Precondiciones:
(Ninguna)

Flujo principal:
Paso 1: El organizador ingresa a inscribir club
Paso 2: El sistema solicita los datos del nuevo club a inscribir
Paso 3: El organizador carga los datos
Paso 4: El sistema verifica que el nombre del club no se encuentre en uso
Paso 5: El sistema solicita una confirmación
Paso 6: El organizador confirma los datos
Paso 7: El sistema registra el nuevo club

Flujos Alternativos:
Alt 1 (Paso 1):
- El sistema notifica que el nombre solicitado no se encuentra disponible
- El sistema solicita un nuevo nombre
- El usuario ingresa el nuevo nombre
Alt 2 (Paso 1):
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
Paso 2: El organizador selecciona el estadio que desea modificar
Paso 3: El organizador selecciona el atributo que desea modificar
Paso 4: El sistema solicita el nuevo valor para el atributo
Paso 5: El organizador carga el nuevo valor
Paso 6: El sistema solicita una confirmación para registrar el cambio
Paso 7: El organizador confirma
Paso 8: El sistema registra el cambio

Flujos Alternativos:
Alt 1 (Paso 1):
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
Alt 1 (Paso 1):
- El organizador deniega
- El sistema ofrece corregir los datos
- El organizador corrige los datos
- El sistema registra los cambios

Notas: -

================================================================================
CASO DE USO: CU-09 - Registrar evento
================================================================================
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

Notas: -

================================================================================
CASO DE USO: CU-17 - Registrar tarjetas
================================================================================
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

Notas: -

================================================================================
CASO DE USO: CU-18 - Registrar cambios
================================================================================
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

Notas: -
