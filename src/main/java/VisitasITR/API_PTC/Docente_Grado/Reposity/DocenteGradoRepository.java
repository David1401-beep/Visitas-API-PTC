package VisitasITR.API_PTC.Docente_Grado.Reposity;

import VisitasITR.API_PTC.Docente_Grado.Entity.DocenteGradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteGradoRepository extends JpaRepository<DocenteGradoEntity, Long> {
    boolean existsByDocente_IdDocenteAndGrado_IdGradoAndAnioEscolar(
            Long idDocente,
            Long idGrado,
            Integer anioEscolar
    );
}
