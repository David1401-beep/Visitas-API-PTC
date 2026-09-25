-- Tabla COMUNICADO: la usa ComunicadoEntity de la API de negocio (puerto 8080).
-- Definicion identica a la de PTC.sql (linea 29), que no llego a ejecutarse.
-- Sin ella, /api/v1/comunicados/por-docente/{id} responde 500 (ORA-00942).

-- Sin esto SQL*Plus corta la sentencia en la primera linea en blanco.
SET SQLBLANKLINES ON

CREATE TABLE COMUNICADO (
    ID_COMUNICADO      NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_DOCENTE         NUMBER          NOT NULL,
    COM_MENSAJE        VARCHAR2(500)   NOT NULL,
    COM_FECHA          TIMESTAMP       DEFAULT SYSTIMESTAMP NOT NULL,

    -- Permite retirar un comunicado sin borrarlo, para conservar el
    -- historial de lo que se publico.
    COM_ACTIVO         CHAR(1)         DEFAULT 'S' NOT NULL,

    CONSTRAINT COMUNICADO_PK       PRIMARY KEY (ID_COMUNICADO),
    CONSTRAINT COMUNICADO_DOC_FK   FOREIGN KEY (ID_DOCENTE)
                                   REFERENCES DOCENTE (ID_DOCENTE),
    CONSTRAINT COMUNICADO_ACTIVO_CK CHECK (COM_ACTIVO IN ('S', 'N')),
    CONSTRAINT COMUNICADO_MENSAJE_CK CHECK (LENGTH(TRIM(COM_MENSAJE)) > 0)
);

CREATE INDEX COMUNICADO_FECHA_IX ON COMUNICADO (COM_FECHA DESC);

INSERT INTO COMUNICADO (ID_DOCENTE, COM_MENSAJE)
VALUES (1, 'Reunion General de padres de familia el 20 de junio a las 8:00 A.M. en el auditorio.');

INSERT INTO COMUNICADO (ID_DOCENTE, COM_MENSAJE)
VALUES (1, 'Entrega de notas del segundo trimestre el 15 de septiembre a las 2:00 P.M.');

INSERT INTO COMUNICADO (ID_DOCENTE, COM_MENSAJE)
VALUES (11, 'Los estudiantes de tercer ano tecnico deben presentar su anteproyecto antes del 30 de septiembre.');

COMMIT;

EXIT;
