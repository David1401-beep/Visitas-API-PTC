package VisitasITR.API_PTC.Academica.Services;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import java.util.List;

public interface AcademicaService {

    List<AcademicaDTO> listarTodos();

    AcademicaDTO buscarPorId(Long id);

    AcademicaDTO guardar(AcademicaDTO dto);

    AcademicaDTO actualizar(Long id, AcademicaDTO dto); // PUT

    AcademicaDTO actualizarAcademica(Long id, AcademicaDTO dto); // PATCH

    void eliminar(Long id);

    boolean eliminar2(Long id);
}