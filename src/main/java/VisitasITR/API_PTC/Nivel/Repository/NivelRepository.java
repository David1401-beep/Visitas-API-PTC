package VisitasITR.API_PTC.Nivel.Repository;

import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends JpaRepository<NivelEntity, Long> {
}
