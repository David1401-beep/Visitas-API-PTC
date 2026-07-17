package VisitasITR.API_PTC.Especialidad.Repository;

import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository

public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Integer>{

}
