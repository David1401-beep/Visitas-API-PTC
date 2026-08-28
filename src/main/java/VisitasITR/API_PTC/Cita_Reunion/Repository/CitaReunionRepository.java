package VisitasITR.API_PTC.Cita_Reunion.Repository;

import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaReunionRepository extends JpaRepository<CitaReunionEntity, Long> {

    List<CitaReunionEntity> findByDocente_IdDocenteOrderByCitFechaReunionDesc(Long idDocente);

    List<CitaReunionEntity> findAllByOrderByCitFechaReunionDesc();

    List<CitaReunionEntity> findByDocente_IdDocenteAndCitEstadoOrderByCitFechaReunionDesc(
            Long idDocente, String citEstado);

    List<CitaReunionEntity> findByDocente_IdDocenteAndCitMotivoContainingIgnoreCaseOrderByCitFechaReunionDesc(
            Long idDocente, String citMotivo);

    List<CitaReunionEntity> findByEstudianteEncargado_IdEstudianteEncargadoOrderByCitFechaReunionDesc(
            Long idEstudianteEncargado);

    boolean existsByDocente_IdDocente(Long idDocente);
}