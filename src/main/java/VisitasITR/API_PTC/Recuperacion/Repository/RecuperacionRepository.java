package VisitasITR.API_PTC.Recuperacion.Repository;

import VisitasITR.API_PTC.Recuperacion.Entity.RecuperacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RecuperacionRepository extends JpaRepository<RecuperacionEntity, Long> {

    // El codigo vigente es el ultimo que se pidio y que todavia no se usa.
    Optional<RecuperacionEntity> findFirstByCorreoAndUsadoOrderByIdDesc(String correo, Integer usado);

    // Al pedir un codigo nuevo, los anteriores dejan de servir.
    @Modifying
    @Query("UPDATE RecuperacionEntity r SET r.usado = 1 WHERE r.correo = :correo AND r.usado = 0")
    int invalidarAnteriores(@Param("correo") String correo);

    @Modifying
    @Query("UPDATE RecuperacionEntity r SET r.intentos = r.intentos + 1 WHERE r.id = :id")
    int sumarIntento(@Param("id") Long id);
}
