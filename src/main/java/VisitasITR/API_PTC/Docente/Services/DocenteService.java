package VisitasITR.API_PTC.Docente.Services;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;

import java.util.List;

public interface DocenteService {
    List<DocenteEntity> listarTodos();

    DocenteEntity buscarPorId(Long id);

    DocenteEntity guardar(DocenteDTO dto);

    DocenteEntity actualizar(Long id, DocenteDTO dto);

    void eliminar(Long id);
}