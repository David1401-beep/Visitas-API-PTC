package VisitasITR.API_PTC.Detalle_Grado.Services;

import VisitasITR.API_PTC.Detalle_Grado.DTO.DetalleGradoDTO;
import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;

import java.util.List;

public interface DetalleGradoService {
    List<DetalleGradoEntity> listarTodos();

    DetalleGradoEntity buscarPorId(Long id);

    DetalleGradoEntity guardar(DetalleGradoDTO dto);

    DetalleGradoEntity actualizar(Long id, DetalleGradoDTO dto);

    void eliminar(Long id);

    DetalleGradoDTO actualizarDetalleGrado(Long id, DetalleGradoDTO dto);

    boolean eliminar2(Long id);
}

