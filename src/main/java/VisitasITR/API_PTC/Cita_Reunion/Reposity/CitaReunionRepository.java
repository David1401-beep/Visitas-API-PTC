package VisitasITR.API_PTC.Cita_Reunion.Reposity;

import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CitaReunionRepository extends JpaRepository<CitaReunionEntity, Long> {

    List<CitaReunionEntity> findAllByEmpleado_IdEmpleadoOrderByFechaReunionDesc(Long idEmpleado);

    List<CitaReunionEntity> findAllByEstudianteEncargado_IdEstudianteEncargadoInOrderByFechaReunionAsc(
            Collection<Long> idsEstudianteEncargado
    );
}
