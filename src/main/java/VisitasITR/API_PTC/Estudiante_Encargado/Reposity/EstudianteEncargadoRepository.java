package VisitasITR.API_PTC.Estudiante_Encargado.Reposity;

import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EstudianteEncargadoRepository extends JpaRepository<EstudianteEncargadoEntity, Long> {

    List<EstudianteEncargadoEntity> findAllByEstudiante_IdEstudianteIn(
            Collection<Long> idsEstudiante
    );
}
