package VisitasITR.API_PTC.Estudiante.Repository;

import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    boolean existsByEstCorreo(String estCorreo);
    boolean existsByEstCodigo(String estCodigo);
}