package VisitasITR.API_PTC.Materia_Docente.Repository;

import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaDocenteRepository extends JpaRepository<MateriaDocenteEntity, Long> {

    boolean existsByDocente_IdDocente(Long idDocente);

    boolean existsByMateria_IdMateria(Long idMateria);

    List<MateriaDocenteEntity> findByDocente_IdDocente(Long idDocente);

    List<MateriaDocenteEntity> findByMateria_IdMateria(Long idMateria);

    boolean existsByMateria_IdMateriaAndDocente_IdDocente(Long idMateria, Long idDocente);
}