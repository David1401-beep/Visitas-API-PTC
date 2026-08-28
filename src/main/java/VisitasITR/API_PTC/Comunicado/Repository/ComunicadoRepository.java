package VisitasITR.API_PTC.Comunicado.Repository;

import VisitasITR.API_PTC.Comunicado.Entity.ComunicadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComunicadoRepository extends JpaRepository<ComunicadoEntity, Long> {

    // Comunicados visibles para los encargados, del mas reciente al mas antiguo.
    List<ComunicadoEntity> findByComActivoOrderByComFechaDesc(String comActivo);

    // Comunicados de un docente, incluidos los retirados.
    List<ComunicadoEntity> findByDocente_IdDocenteOrderByComFechaDesc(Long idDocente);

    // Busqueda por texto dentro del mensaje de los comunicados visibles.
    List<ComunicadoEntity> findByComActivoAndComMensajeContainingIgnoreCaseOrderByComFechaDesc(
            String comActivo, String comMensaje);

    List<ComunicadoEntity> findByComActivoAndDocente_IdDocenteOrderByComFechaDesc(
            String comActivo, Long idDocente);

    boolean existsByDocente_IdDocente(Long idDocente);
}