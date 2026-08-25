package VisitasITR.API_PTC.Estudiante.Repository;

import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    Optional<EstudianteEntity> findByEstNie(String estNie);
    boolean existsByEstNie(String estNie);
}