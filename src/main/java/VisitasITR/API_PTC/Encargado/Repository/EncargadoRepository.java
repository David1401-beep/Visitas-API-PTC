package VisitasITR.API_PTC.Encargado.Repository;

import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncargadoRepository extends JpaRepository<EncargadoEntity, Long> {
    boolean existsByEncTelefono(String encTelefono);
}