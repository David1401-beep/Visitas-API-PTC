-- Guarda los codigos que se envian por correo para recuperar la contrasena.
-- El codigo no se guarda tal cual: va cifrado con BCrypt igual que una
-- contrasena, para que no sirva de nada si alguien abre la tabla.

SET SQLBLANKLINES ON

CREATE TABLE RECUPERACION_PASSWORD (
    ID_RECUPERACION  NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    REC_CORREO       VARCHAR2(150) NOT NULL,
    REC_CODIGO_HASH  VARCHAR2(60)  NOT NULL,
    REC_EXPIRA       TIMESTAMP     NOT NULL,
    REC_USADO        NUMBER(1)     DEFAULT 0 NOT NULL,
    REC_INTENTOS     NUMBER(2)     DEFAULT 0 NOT NULL,
    REC_CREADO       TIMESTAMP     DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT CK_RECUPERACION_USADO CHECK (REC_USADO IN (0, 1))
);

-- Se busca siempre por correo, y solo el codigo vigente.
CREATE INDEX IDX_RECUPERACION_CORREO ON RECUPERACION_PASSWORD (REC_CORREO, REC_USADO);

EXIT;
