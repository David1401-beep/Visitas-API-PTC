package VisitasITR.API_PTC.Docente.Repository;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {

    boolean existsByDocCorreo(String docCorreo);

    boolean existsByDocCorreoAndIdDocenteNot(String docCorreo, Long idDocente);
}