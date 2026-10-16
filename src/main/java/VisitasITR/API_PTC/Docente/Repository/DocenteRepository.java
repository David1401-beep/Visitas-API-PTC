package VisitasITR.API_PTC.Docente.Repository;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {

    boolean existsByDocCorreo(String docCorreo);

    boolean existsByDocCorreoAndIdDocenteNot(String docCorreo, Long idDocente);

    @Query("SELECT d.docClave FROM DocenteEntity d")
    List<String> obtenerClaves();
}