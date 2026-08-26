package VisitasITR.API_PTC.Estudiante.Repository;

import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
<<<<<<< HEAD:src/main/java/VisitasITR/API_PTC/Estudiante/Repository/EstudianteRepository.java
    Optional<EstudianteEntity> findByEstNie(String estNie);
    boolean existsByEstNie(String estNie);
}
=======

    boolean existsByEstCodigo(String estCodigo);

    Optional<EstudianteEntity> findByEstCodigo(String estCodigo);

    List<EstudianteEntity> findAllByUsuarioEstudiante_IdUsuario(Long idUsuario);
}
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4:src/main/java/VisitasITR/API_PTC/Estudiante/Reposity/EstudianteRepository.java
