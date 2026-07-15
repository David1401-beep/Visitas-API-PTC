package VisitasITR.API_PTC.Especialidad.Service;


import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;

import java.util.List;

public interface EspecialidadService {
    List<EspecialidadDTO> listar();
    EspecialidadDTO obtenerPorId(Integer id);
    EspecialidadDTO guardar(EspecialidadDTO dto);
    EspecialidadDTO actualizar(Integer id, EspecialidadDTO dto);
    void eliminar(Integer id);
}
