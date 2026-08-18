package VisitasITR.API_PTC.Encargado.Reposity;

import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncargadoRepository extends JpaRepository<EncargadoEntity, Long> {


    boolean existsByTelefono(String telefono);


    Optional<EncargadoEntity> findByTelefono(String telefono);
}