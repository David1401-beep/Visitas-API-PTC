package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;

import java.util.List;

public interface EstudianteService {
    List<EstudianteEntity> listarTodos();
    EstudianteEntity buscarPorId(Long id);
    EstudianteEntity guardar(EstudianteDTO dto);
    EstudianteEntity actualizar(Long id, EstudianteDTO dto);
    void eliminar(Long id);
    EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO dto);
    boolean eliminar2(Long id);
}