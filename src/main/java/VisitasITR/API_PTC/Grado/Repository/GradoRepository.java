package VisitasITR.API_PTC.Grado.Repository;

import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradoRepository extends JpaRepository<GradoEntity, Long> {}