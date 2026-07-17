package VisitasITR.API_PTC.Estudiante.Service;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;

import java.util.List;

public interface EstudianteService {
    List<EstudianteDTO> listar();
    EstudianteDTO obtenerPorId(Integer id);
    EstudianteDTO guardar(EstudianteDTO dto);
    EstudianteDTO actualizar(Integer id, EstudianteDTO dto);
    void eliminar(Integer id);

}
