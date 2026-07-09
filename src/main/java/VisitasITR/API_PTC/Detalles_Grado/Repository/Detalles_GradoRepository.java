package VisitasITR.API_PTC.Detalles_Grado.Repository;

import VisitasITR.API_PTC.Detalles_Grado.Entity.Detalles_GradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Detalles_GradoRepository extends JpaRepository<Detalles_GradoEntity, Long> {


}
