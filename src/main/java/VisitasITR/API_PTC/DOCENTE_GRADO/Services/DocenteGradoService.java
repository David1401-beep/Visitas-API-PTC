package VisitasITR.API_PTC.DOCENTE_GRADO.Services;

import VisitasITR.API_PTC.DOCENTE_GRADO.DTO.DocenteGradoDTO;
import VisitasITR.API_PTC.DOCENTE_GRADO.Entity.DocenteGradoEntity;

import java.util.List;

public interface DocenteGradoService {
    List<DocenteGradoEntity> listarTodos();

    DocenteGradoEntity buscarPorId(Long id);

    DocenteGradoEntity guardar(DocenteGradoDTO dto);

    DocenteGradoEntity actualizar(Long id, DocenteGradoDTO dto);

    void eliminar(Long id);

    DocenteGradoDTO actualizarDocenteGrado(Long id, DocenteGradoDTO dto);

    boolean eliminar2(Long id);
}