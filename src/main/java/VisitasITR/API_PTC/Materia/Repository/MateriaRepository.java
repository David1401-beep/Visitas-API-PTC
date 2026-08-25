package VisitasITR.API_PTC.Materia.Repository;

import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdMateriaNot(String nombre, Long idMateria);
}