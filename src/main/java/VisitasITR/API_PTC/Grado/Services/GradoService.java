package VisitasITR.API_PTC.Grado.Services;

import VisitasITR.API_PTC.Grado.DTO.GradoDTO;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;

import java.util.List;

public interface GradoService {
    List<GradoEntity> listarTodos();

    GradoEntity buscarPorId(Long id);

    GradoEntity guardar(GradoDTO dto);

    GradoEntity actualizar(Long id, GradoDTO dto);

    void eliminar(Long id);
}