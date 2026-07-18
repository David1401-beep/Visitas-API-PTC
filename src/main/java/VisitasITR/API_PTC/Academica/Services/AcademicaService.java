package VisitasITR.API_PTC.Academica.Services;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;

import java.util.List;

public interface AcademicaService {
    List<AcademicaEntity> listarTodos();

    AcademicaEntity buscarPorId(Long id);

    AcademicaEntity guardar(AcademicaDTO dto);

    AcademicaEntity actualizar(Long id, AcademicaDTO dto);

    void eliminar(Long id);
}
