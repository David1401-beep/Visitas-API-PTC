package VisitasITR.API_PTC.Docente_Grado.Repository;

import VisitasITR.API_PTC.Docente_Grado.Entity.Docente_GradoEntity;
import VisitasITR.API_PTC.Docente_Grado.Entity.Docente_GradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Docente_GradoRepository extends JpaRepository<Docente_GradoEntity, Long> {
    boolean existsByDocente_IdDocenteAndGrado_IdGradoAndAnioEscolar(Long idDocente, Long idGrado, Integer anioEscolar);
}