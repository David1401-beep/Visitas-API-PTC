package VisitasITR.API_PTC.Estudiante_Encargado.Repository;

import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteEncargadoRepository extends JpaRepository<EstudianteEncargadoEntity, Long> {

    boolean existsByEstudianteIdEstudianteAndEncargadoIdEncargado(Long idEstudiante, Long idEncargado);

    boolean existsByEstudianteIdEstudianteAndEncargadoIdEncargadoAndIdEstudianteEncargadoNot(
            Long idEstudiante, Long idEncargado, Long idEstudianteEncargado
    );
}