-- La funcion de posponer citas guarda el estado POSPUESTA, pero la
-- restriccion original de PTC.sql no lo incluia, asi que Oracle rechazaba
-- el cambio y la app mostraba "No se pudo posponer".
--
-- Aqui rehago la restriccion agregando ese estado.

SET SQLBLANKLINES ON

ALTER TABLE CITA_REUNION DROP CONSTRAINT CITA_ESTADO_CK;

ALTER TABLE CITA_REUNION ADD CONSTRAINT CITA_ESTADO_CK
    CHECK (CIT_ESTADO IN ('PENDIENTE', 'ACEPTADA', 'RECHAZADA',
                          'CANCELADA', 'FINALIZADA', 'POSPUESTA'));

COMMIT;

EXIT;
