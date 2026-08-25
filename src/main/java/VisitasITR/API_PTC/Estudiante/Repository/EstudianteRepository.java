package VisitasITR.API_PTC.Estudiante.Reposity;

import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {

    boolean existsByEstCodigo(String estCodigo);

    Optional<EstudianteEntity> findByEstCodigo(String estCodigo);
}