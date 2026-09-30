# Notas para desplegar en Heroku

Lo que ya esta listo en el repositorio: el `Procfile`, el `system.properties`
con Java 17 y las pruebas configuradas para que el build no necesite Oracle.
El puerto tambien se toma solo del que asigne Heroku.

## Variables a cargar en Heroku

En el panel: **Settings → Config Vars**. El `.env` no se usa alla; estas
variables se cargan una por una.

| Variable | Que va | Ojo |
| --- | --- | --- |
| `DB_URL` | Cadena de la base en Oracle Cloud | **No** puede ser `localhost` |
| `DB_USER` | Usuario de la base | |
| `DB_PASSWORD` | Clave de la base | |
| `DB_DRIVER` | `oracle.jdbc.OracleDriver` | |
| `DB_DIALECT` | `org.hibernate.dialect.OracleDialect` | |
| `JWT_SECRET` | El mismo texto que se usa en local | Minimo 32 caracteres |
| `JWT_EXPIRATION_MS` | `900000` | 15 minutos |
| `CORS_ORIGINS` | La URL del sitio web ya desplegado | Separadas por coma, sin `/` al final |
| `COOKIE_SECURE` | `true` | En produccion va con HTTPS |
| `COOKIE_SAME_SITE` | `None` | Si el frontend queda en otro dominio |
| `MAIL_HOST` | `smtp.gmail.com` | |
| `MAIL_PORT` | `587` | |
| `MAIL_USER` | El correo desde el que se envia | |
| `MAIL_PASSWORD` | La contrasena de aplicacion de Gmail | 16 letras, no la de la cuenta |
| `MAIL_REMITENTE` | El mismo correo de `MAIL_USER` | |
| `RECUPERACION_MINUTOS` | `10` | |
| `RECUPERACION_INTENTOS` | `3` | |
| `CLOUDINARY_CLOUD_NAME` | De la cuenta de Cloudinary | |
| `CLOUDINARY_API_KEY` | De la cuenta de Cloudinary | |
| `CLOUDINARY_API_SECRET` | De la cuenta de Cloudinary | |

`PORT` no se pone: Heroku la asigna solo.

## Scripts que hay que correr en la base nueva

En orden, sobre el esquema ya migrado a Oracle Cloud:

1. `database/vista_usuarios_auth_con_origen.sql` — la vista del login, con la
   columna que dice de que tabla salio cada usuario
2. `database/crear_tabla_recuperacion.sql` — la tabla de codigos de recuperacion

Sin la vista, cualquier intento de login responde error 500.

## Direcciones ya desplegadas

| Que | Donde |
| --- | --- |
| API (Heroku) | `https://gestor-de-visitas-itr-53fe7294e1e4.herokuapp.com` |
| Sitio web (Vercel) | `https://visitas-itr-web.vercel.app` |

Ya quedaron puestas en los dos `js/config.js`, en la constante
`API_EN_LA_NUBE`. Los archivos detectan solos donde corren: en `localhost`
usan la API local del puerto 8080, y publicados usan la de Heroku.

Y la del sitio web ya esta en `CORS_ORIGINS` del `.env`. Falta cargarla
igual en las Config Vars de Heroku, porque ese archivo no se lee alla.

## Pendiente de decidir

El frontend y la API quedan en dominios distintos, asi que la cookie de sesion
pasa a ser de terceros. Con `COOKIE_SECURE=true` y `SameSite=None` funciona,
pero los navegadores estan restringiendo ese tipo de cookies.

La alternativa es poner un proxy en el frontend (una regla de reescritura que
mande `/api/*` a Heroku). Asi el navegador ve un solo dominio y el problema
desaparece. Queda a decision del equipo.
