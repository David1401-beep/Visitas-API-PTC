package VisitasITR.API_PTC.Grupo.Repository;

import VisitasITR.API_PTC.Grupo.Entity.GrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<GrupoEntity, Long> {
}
