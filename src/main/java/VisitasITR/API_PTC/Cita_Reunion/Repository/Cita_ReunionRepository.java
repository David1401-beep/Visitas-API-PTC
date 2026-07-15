package VisitasITR.API_PTC.Cita_Reunion.Repository;

import VisitasITR.API_PTC.Cita_Reunion.Entity.Cita_ReunionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cita_ReunionRepository extends JpaRepository<Cita_ReunionEntity, Long> {

}
