-- Tabla donde guardo los datos de las imagenes que subo a Cloudinary.
-- La imagen se queda en Cloudinary; aqui solo guardo la direccion y el
-- identificador que me devuelve, que sirve para poder borrarla despues.

SET SQLBLANKLINES ON

CREATE TABLE IMAGEN (
    ID_IMAGEN          NUMBER GENERATED ALWAYS AS IDENTITY,
    IMG_NOMBRE         VARCHAR2(200),
    IMG_URL            VARCHAR2(300)   NOT NULL,
    IMG_CLOUDINARY_ID  VARCHAR2(200)   NOT NULL,
    IMG_FECHA          TIMESTAMP       DEFAULT SYSTIMESTAMP NOT NULL,

    CONSTRAINT IMAGEN_PK PRIMARY KEY (ID_IMAGEN),
    CONSTRAINT IMAGEN_CLOUDINARY_UQ UNIQUE (IMG_CLOUDINARY_ID)
);

COMMIT;

EXIT;
