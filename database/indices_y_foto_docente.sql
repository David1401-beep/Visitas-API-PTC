-- Optimizacion de consultas e integracion con Cloudinary.
-- Ejecutar como DAVIDRAMIREZ123 sobre XEPDB1.

SET SQLBLANKLINES ON

-- 1. URL de la foto de perfil del docente (la imagen vive en el CDN).
ALTER TABLE DOCENTE ADD (DOC_FOTO_URL VARCHAR2(300));

-- 2. Indices B-Tree sobre las columnas que mas se filtran y ordenan.

-- Login: la busqueda por correo de V_USUARIOS_AUTH ya viaja sobre los indices
-- unicos que Oracle crea con las restricciones UNIQUE de cada tabla
-- (ADM_CORREO_UQ, DOC_CORREO_UQ, REC_CORREO_UQ, EST_CORREO_UQ), asi que no se
-- duplican aqui: un indice extra sobre la misma columna solo costaria escrituras.

-- Agenda: las citas se listan por docente y por fecha, y se filtran por estado.
CREATE INDEX CITA_DOCENTE_FECHA_IX   ON CITA_REUNION (ID_DOCENTE, CIT_FECHA_REUNION);
CREATE INDEX CITA_ESTADO_IX          ON CITA_REUNION (CIT_ESTADO);

-- Comunicados por autor, ordenados por fecha descendente.
CREATE INDEX COMUNICADO_DOC_FECHA_IX ON COMUNICADO (ID_DOCENTE, COM_FECHA DESC);

-- Fichas de alumnado: se agrupan por grado y por especialidad academica.
CREATE INDEX ESTUDIANTE_GRADO_IX     ON ESTUDIANTE (ID_GRADO);
CREATE INDEX ESTUDIANTE_ACADEMICA_IX ON ESTUDIANTE (ID_ACADEMICA);

-- Relacion estudiante-encargado, recorrida en ambos sentidos.
CREATE INDEX EST_ENC_ESTUDIANTE_IX   ON ESTUDIANTE_ENCARGADO (ID_ESTUDIANTE);
CREATE INDEX EST_ENC_ENCARGADO_IX    ON ESTUDIANTE_ENCARGADO (ID_ENCARGADO);

COMMIT;

EXIT;
