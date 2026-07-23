package VisitasITR.API_PTC.Nivel.Services;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;

import java.util.List;

public interface NivelService {
    List<NivelEntity> listarTodos();

    NivelEntity buscarPorId(Long id);

    NivelEntity guardar(NivelDTO dto);

    NivelEntity actualizar(Long id, NivelDTO dto);

    void eliminar(Long id);

    NivelDTO actualizarNivel(Long id, NivelDTO dto);

    boolean eliminar2(Long id);
}