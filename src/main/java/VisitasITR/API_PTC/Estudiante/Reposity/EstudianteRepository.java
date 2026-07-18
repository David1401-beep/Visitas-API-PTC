package VisitasITR.API_PTC.Estudiante.Reposity;

import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
}