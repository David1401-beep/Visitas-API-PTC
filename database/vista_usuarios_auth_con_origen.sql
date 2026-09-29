-- Vuelve a crear V_USUARIOS_AUTH agregando USU_ORIGEN.
-- La vista es un UNION ALL de cuatro tablas, asi que Oracle no deja
-- hacerle UPDATE. Con esta columna la API sabe en que tabla escribir
-- cuando alguien recupera su contrasena.

-- Sin esto SQL*Plus corta la sentencia en la primera linea en blanco.
SET SQLBLANKLINES ON

CREATE OR REPLACE VIEW V_USUARIOS_AUTH AS
    SELECT ID_ADMINISTRADOR AS ID_USUARIO,
           ADM_CORREO       AS USU_EMAIL,
           ADM_PASSWORD     AS USU_PASSWORD,
           ADM_ROL          AS USU_ROL,
           'ADMINISTRADOR'  AS USU_ORIGEN
    FROM   ADMINISTRADOR

    UNION ALL

    SELECT ID_DOCENTE,
           DOC_CORREO,
           DOC_PASSWORD,
           -- Se envia DOC_TIPO en lugar de DOC_ROL porque este ultimo
           -- siempre vale 'DOCENTE' y no distingue al docente tecnico
           -- del academico.
           DOC_TIPO,
           'DOCENTE'
    FROM   DOCENTE

    UNION ALL

    SELECT ID_RECEPCIONISTA,
           REC_CORREO,
           REC_PASSWORD,
           REC_ROL,
           'RECEPCIONISTA'
    FROM   RECEPCIONISTA

    UNION ALL

    SELECT ID_ESTUDIANTE,
           EST_CORREO,
           EST_PASSWORD,
           EST_ROL,
           'ESTUDIANTE'
    FROM   ESTUDIANTE;

EXIT;
