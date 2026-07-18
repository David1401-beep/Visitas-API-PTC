package VisitasITR.API_PTC.Detalle_Grado.Reposity;

import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleGradoRepository extends JpaRepository<DetalleGradoEntity, Long> {
}

