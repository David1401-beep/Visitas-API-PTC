package VisitasITR.API_PTC.Cita_Reunion.Repository;

import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaReunionRepository extends JpaRepository<CitaReunionEntity, Long> {
}