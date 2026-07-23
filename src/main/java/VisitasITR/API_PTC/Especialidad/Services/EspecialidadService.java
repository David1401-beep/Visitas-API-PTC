package VisitasITR.API_PTC.Especialidad.Services;

import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;

import java.util.List;

public interface EspecialidadService {
    List<EspecialidadEntity> listarTodos();

    EspecialidadEntity buscarPorId(Long id);

    EspecialidadEntity guardar(EspecialidadDTO dto);

    EspecialidadEntity actualizar(Long id, EspecialidadDTO dto);

    void eliminar(Long id);

    EspecialidadDTO actualizarEspecialidad(Long id, EspecialidadDTO dto);

    boolean eliminar2(Long id);
}