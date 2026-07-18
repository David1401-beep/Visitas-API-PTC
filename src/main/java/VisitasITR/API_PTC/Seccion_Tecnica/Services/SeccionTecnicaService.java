package VisitasITR.API_PTC.Seccion_Tecnica.Services;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;

import java.util.List;

public interface SeccionTecnicaService {
    List<SeccionTecnicaEntity> listarTodos();

    SeccionTecnicaEntity buscarPorId(Long id);

    SeccionTecnicaEntity guardar(SeccionTecnicaDTO dto);

    SeccionTecnicaEntity actualizar(Long id, SeccionTecnicaDTO dto);

    void eliminar(Long id);
}