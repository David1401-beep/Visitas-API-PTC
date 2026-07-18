package VisitasITR.API_PTC.Academica.Reposity;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicaRepository extends JpaRepository<AcademicaEntity, Long> {
}
