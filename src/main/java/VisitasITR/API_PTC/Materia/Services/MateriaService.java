package VisitasITR.API_PTC.Materia.Services;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;

import java.util.List;

public interface MateriaService {
    List<MateriaEntity> listarTodos();

    MateriaEntity buscarPorId(Long id);

    MateriaEntity guardar(MateriaDTO dto);

    MateriaEntity actualizar(Long id, MateriaDTO dto);

    void eliminar(Long id);
}