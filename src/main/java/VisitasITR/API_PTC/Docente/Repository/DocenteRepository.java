package VisitasITR.API_PTC.Docente.Repository;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {
}
