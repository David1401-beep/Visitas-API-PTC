package VisitasITR.API_PTC.Academica.Repository;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicaRepository extends JpaRepository<AcademicaEntity, Long> {

    boolean existsBySeccionIgnoreCase(String seccion);
    boolean existsBySeccionIgnoreCaseAndIdAcademicaNot(String seccion, Long idAcademica);
}