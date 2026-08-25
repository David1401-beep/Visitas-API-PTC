package VisitasITR.API_PTC.Seccion_Tecnica.Reposity;

import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeccionTecnicaRepository extends JpaRepository<SeccionTecnicaEntity, Long> {

    boolean existsByTecnicaIgnoreCase(String tecnica);

    boolean existsByTecnicaIgnoreCaseAndIdTecnicaNot(String tecnica, Long idTecnica);
}