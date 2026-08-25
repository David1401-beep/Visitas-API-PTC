package VisitasITR.API_PTC.Recepcionista.Repository;

import VisitasITR.API_PTC.Recepcionista.Entity.RecepcionistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepcionistaRepository extends JpaRepository<RecepcionistaEntity, Long> {

    boolean existsByRecCorreo(String recCorreo);

    boolean existsByRecCorreoAndIdRecepcionistaNot(String recCorreo, Long idRecepcionista);
}