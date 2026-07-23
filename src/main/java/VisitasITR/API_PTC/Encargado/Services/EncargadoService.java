package VisitasITR.API_PTC.Encargado.Services;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;

import java.util.List;

public interface EncargadoService {
    List<EncargadoEntity> listarTodos();

    EncargadoEntity buscarPorId(Long id);

    EncargadoEntity guardar(EncargadoDTO dto);

    EncargadoEntity actualizar(Long id, EncargadoDTO dto);

    void eliminar(Long id);

    EncargadoDTO actualizarEncargado(Long id, EncargadoDTO dto);

    boolean eliminar2(Long id);

}
