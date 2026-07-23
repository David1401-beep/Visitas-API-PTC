package VisitasITR.API_PTC.Materia_Docente.Services;

import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;

import java.util.List;

public interface MateriaDocenteService {
    List<MateriaDocenteEntity> listarTodos();

    MateriaDocenteEntity buscarPorId(Long id);

    MateriaDocenteEntity guardar(MateriaDocenteDTO dto);

    MateriaDocenteEntity actualizar(Long id, MateriaDocenteDTO dto);

    void eliminar(Long id);

    MateriaDocenteDTO actualizarMateriaDocente(Long id, MateriaDocenteDTO dto);

    boolean eliminar2(Long id);
}