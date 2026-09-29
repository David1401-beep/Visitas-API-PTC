package VisitasITR.API_PTC.Recuperacion.Repository;

import VisitasITR.API_PTC.Auth.Entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// V_USUARIOS_AUTH es un UNION ALL de cuatro tablas y Oracle no deja
// hacerle UPDATE. Por eso aqui escribo directo en cada tabla, y el
// servicio elige cual segun USU_ORIGEN.
@Repository
public interface PasswordRepository extends JpaRepository<AuthEntity, String> {

    @Modifying
    @Query(value = "UPDATE ADMINISTRADOR SET ADM_PASSWORD = :hash WHERE LOWER(ADM_CORREO) = :correo",
            nativeQuery = true)
    int actualizarAdministrador(@Param("correo") String correo, @Param("hash") String hash);

    @Modifying
    @Query(value = "UPDATE DOCENTE SET DOC_PASSWORD = :hash WHERE LOWER(DOC_CORREO) = :correo",
            nativeQuery = true)
    int actualizarDocente(@Param("correo") String correo, @Param("hash") String hash);

    @Modifying
    @Query(value = "UPDATE RECEPCIONISTA SET REC_PASSWORD = :hash WHERE LOWER(REC_CORREO) = :correo",
            nativeQuery = true)
    int actualizarRecepcionista(@Param("correo") String correo, @Param("hash") String hash);

    @Modifying
    @Query(value = "UPDATE ESTUDIANTE SET EST_PASSWORD = :hash WHERE LOWER(EST_CORREO) = :correo",
            nativeQuery = true)
    int actualizarEstudiante(@Param("correo") String correo, @Param("hash") String hash);
}
