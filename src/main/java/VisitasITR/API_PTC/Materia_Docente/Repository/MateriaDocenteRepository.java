package VisitasITR.API_PTC.Materia_Docente.Repository;

import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaDocenteRepository extends JpaRepository<MateriaDocenteEntity, Long> {}