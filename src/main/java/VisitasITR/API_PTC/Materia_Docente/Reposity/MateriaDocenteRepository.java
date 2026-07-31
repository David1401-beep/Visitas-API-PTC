package VisitasITR.API_PTC.Materia_Docente.Reposity;

import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriaDocenteRepository extends JpaRepository<MateriaDocenteEntity, Long> {
    Optional<MateriaDocenteEntity> findByDocente_IdDocente(Long idDocente);
}
