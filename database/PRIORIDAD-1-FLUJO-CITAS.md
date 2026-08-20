# Prioridad 1: flujo vertical de convocatorias

Este recorrido demuestra que una misma cita atraviesa las cuatro capas del
proyecto:

```text
Docente web -> API -> Oracle -> mobile del encargado
             API <- respuesta del encargado
Docente web <- estado actualizado
```

No se agregó ni modificó ninguna tabla. El flujo utiliza las columnas actuales
de `CITA_REUNION`.

## Cambios principales

- El docente crea la convocatoria desde `Docentes/gestionCitas.html`.
- La web consulta solamente las citas del empleado que inició sesión.
- Mobile consulta solamente las citas de los registros
  `ESTUDIANTE_ENCARGADO` guardados en su sesión.
- Aceptar y posponer ejecutan un `PATCH` real contra la API.
- Posponer guarda `POSPUESTA`, cambia `CIT_FECHA_REUNION` y agrega el motivo a
  `CIT_OBSERVACIONES`.
- La web actualiza el historial al recuperar el foco y cada 15 segundos.

## Endpoints incorporados

```http
GET /api/v1/citas-reuniones/por-empleado/{idEmpleado}
GET /api/v1/citas-reuniones/por-estudiante-encargado?ids=1&ids=2
PATCH /api/v1/citas-reuniones/{idCita}/respuesta-encargado
```

Ejemplo para aceptar:

```json
{
  "idEstudianteEncargado": 1,
  "estado": "ACEPTADA"
}
```

Ejemplo para posponer:

```json
{
  "idEstudianteEncargado": 1,
  "estado": "POSPUESTA",
  "nuevaFechaReunion": "2026-09-25T14:30:00",
  "motivoReprogramacion": "Tengo una cita médica familiar."
}
```

## Preparación

1. Abrir PowerShell como administrador.
2. Iniciar Oracle:

   ```powershell
   Start-Service OracleServiceXE
   Start-Service OracleOraDB21Home1TNSListener
   ```

3. Verificar que ambos servicios aparezcan como `Running`:

   ```powershell
   Get-Service OracleServiceXE, OracleOraDB21Home1TNSListener
   ```

4. En SQL Developer, ejecutar con `F5` el archivo
   `database/inserts_usuarios_prueba.sql`.
5. Iniciar la API en el puerto 8080:

   ```powershell
   mvn spring-boot:run
   ```

6. Servir los proyectos web y mobile mediante Live Server. No abrir los HTML
   directamente con `file://`.

## Prueba completa

### 1. Docente web

Ingresar con:

```text
Correo: docente.prueba@ricaldone.edu.sv
Clave:  Prueba123*
```

Abrir **Gestión de Citas**, crear una convocatoria para `David Eduardo
Ramírez Escobar` y confirmar que aparezca como `Pendiente` en el historial.

### 2. Encargado mobile

Ingresar con:

```text
Correo: estudiante.prueba@ricaldone.edu.sv
Clave:  Prueba123*
```

Abrir **Citas > Convocatorias**. La nueva convocatoria debe mostrar exactamente
el asunto, fecha, hora, descripción y estudiante enviados por el docente.

Elegir una de estas pruebas:

- **Aceptar:** el estado debe cambiar a `Aceptada` y permanecer así después de
  recargar la página.
- **Posponer:** ingresar una fecha y hora futuras y un motivo; el estado debe
  quedar `Pospuesta` después de recargar.

### 3. Regreso a la web

Volver a la pestaña web. El historial se actualiza al recuperar el foco y,
como respaldo, cada 15 segundos. Debe mostrar `Aprobado` o `Pospuesta` sin crear
un registro duplicado.

### 4. Confirmación en Oracle

```sql
SELECT
    ID_CITA,
    ID_EMPLEADO,
    ID_ESTUDIANTE_ENCARGADO,
    CIT_MOTIVO,
    CIT_ESTADO,
    CIT_FECHA_REUNION,
    CIT_OBSERVACIONES
FROM CITA_REUNION
ORDER BY ID_CITA DESC;
```

## Verificaciones automatizadas

```powershell
mvn -q -DskipTests compile
mvn -q -Dtest=CitaReunionServiceTest test
```

Las pruebas cubren:

- aceptación de una convocatoria pendiente;
- propuesta de una nueva fecha y motivo;
- rechazo de una respuesta cuando el ID de `ESTUDIANTE_ENCARGADO` no coincide.

## Límites actuales

- Todavía no existe autenticación con token. Validar el
  `ID_ESTUDIANTE_ENCARGADO` evita una modificación accidental, pero no sustituye
  JWT o una sesión autenticada en el servidor.
- La tabla no distingue entre una convocatoria creada por el docente y una
  solicitud creada por el encargado. Para separar ambos conceptos correctamente
  se necesitará posteriormente una columna de origen o una relación con el
  usuario creador.
- Al posponer se utiliza la misma fecha de la cita y se anexa el motivo a las
  observaciones. Un historial formal de propuestas requerirá otra tabla; no se
  agregó porque esta prioridad no cambia la estructura de la base.
