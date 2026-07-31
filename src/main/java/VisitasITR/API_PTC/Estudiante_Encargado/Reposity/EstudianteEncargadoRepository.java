package VisitasITR.API_PTC.Estudiante_Encargado.Reposity;

import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteEncargadoRepository extends JpaRepository<EstudianteEncargadoEntity, Long> {
    boolean existsByEstudiante_IdEstudianteAndEncargado_IdPadre(Long idEstudiante, Long idPadre);
}
