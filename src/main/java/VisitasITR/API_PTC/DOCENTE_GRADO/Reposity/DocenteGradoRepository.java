package VisitasITR.API_PTC.DOCENTE_GRADO.Reposity;

import VisitasITR.API_PTC.DOCENTE_GRADO.Entity.DocenteGradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteGradoRepository extends JpaRepository<DocenteGradoEntity, Long> {
}
