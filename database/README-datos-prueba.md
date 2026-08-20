# Datos de prueba para usuarios

El archivo `inserts_usuarios_prueba.sql` agrega un conjunto mínimo y coherente
de registros para probar el proyecto de extremo a extremo sin cambiar la
estructura de la base de datos.

## Qué crea

- Un usuario y empleado administrador.
- Un usuario y empleado docente.
- Un usuario con rol `PADRE`, asociado al estudiante como lo espera la API
  actual.
- Un encargado, un estudiante y su registro en `ESTUDIANTE_ENCARGADO`.
- Una cita pendiente para comprobar que la información viaja de la base de
  datos a la API y luego al frontend.
- Los catálogos académicos mínimos que necesita el estudiante.

Los `ID` no se escriben manualmente. Oracle los genera y el script obtiene cada
llave mediante un correo, código u otro dato estable.

## Credenciales de prueba

| Rol | Correo | Contraseña |
| --- | --- | --- |
| Administrador | `admin.prueba@ricaldone.edu.sv` | `Prueba123*` |
| Docente | `docente.prueba@ricaldone.edu.sv` | `Prueba123*` |
| Encargado en mobile | `estudiante.prueba@ricaldone.edu.sv` | `Prueba123*` |

Estas claves son únicamente para desarrollo local. La API actual compara texto
plano; no deben usarse en producción ni en una demostración con datos reales.

## Cómo ejecutarlo

1. Iniciar `OracleServiceXE` y `OracleOraDB21Home1TNSListener`.
2. Abrir Oracle SQL Developer y conectarse con el mismo usuario configurado en
   el archivo `.env` de la API.
3. Abrir `database/inserts_usuarios_prueba.sql`.
4. Ejecutar como script con `F5`, no solamente la sentencia seleccionada.
5. Confirmar que al final aparezcan tres consultas con usuarios, relación del
   estudiante y la cita de prueba.
6. Reiniciar la API y probar los accesos indicados en la tabla anterior.

## Prueba rápida del encargado con Postman

Petición:

```http
POST http://localhost:8080/api/v1/usuarios/inicio-sesion-encargado
Content-Type: application/json
```

Cuerpo:

```json
{
  "correoEstudiante": "estudiante.prueba@ricaldone.edu.sv",
  "password": "Prueba123*"
}
```

La respuesta correcta debe contener `idUsuario`, `idsEstudiante` e
`idsEstudianteEncargado`. Esos últimos identificadores permiten que mobile
consulte únicamente las citas relacionadas con ese estudiante y encargado.

## Limitación que no resuelven los INSERT

En el código y las tablas que usa actualmente la API, `ENCARGADO` no tiene una
columna `ID_USUARIO`. La relación de acceso queda así:

```text
USUARIOS -> ESTUDIANTE.USUARIO_ESTUDIANTE
         -> ESTUDIANTE_ENCARGADO
         -> CITA_REUNION
```

Esto permite probar el flujo que ya está programado, pero significa que la
cuenta representa al estudiante y el encargado utiliza sus credenciales. Si el
objetivo final es que cada padre o madre tenga su propia cuenta, posteriormente
sí sería necesario agregar una relación entre `ENCARGADO` y `USUARIOS` y
actualizar la autenticación. Ese cambio no fue aplicado en este script.

Otra mejora posterior será guardar contraseñas con BCrypt en una API de
autenticación separada. Para ello, `EMP_CLAVE` no debería seguir duplicando la
contraseña de `USUARIOS`; además, su longitud actual de 20 caracteres no admite
un hash BCrypt, que normalmente ocupa 60 caracteres. Tampoco se realizó ese
cambio aquí.
