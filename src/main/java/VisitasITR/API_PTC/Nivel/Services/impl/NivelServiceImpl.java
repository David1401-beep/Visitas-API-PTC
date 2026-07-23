package VisitasITR.API_PTC.Nivel.Services.impl;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
import VisitasITR.API_PTC.Nivel.Services.NivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelServiceImpl implements NivelService {

    private final NivelRepository nivelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NivelEntity> listarTodos() {
        return nivelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public NivelEntity buscarPorId(Long id) {
        return nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public NivelEntity guardar(NivelDTO dto) {
        NivelEntity nivel = NivelEntity.builder()
                .nivel(dto.getNivel())
                .build();
        return nivelRepository.save(nivel);
    }

    @Override
    @Transactional
    public NivelEntity actualizar(Long id, NivelDTO dto) {
        NivelEntity nivel = buscarPorId(id);
        nivel.setNivel(dto.getNivel());
        return nivelRepository.save(nivel);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        NivelEntity nivel = buscarPorId(id);
        nivelRepository.delete(nivel);
    }

    @Override
    public NivelDTO actualizarNivel(Long id, NivelDTO dto) {
        NivelEntity entidadExistente = nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));

        if (dto.getNivel() != null && !dto.getNivel().isBlank()) {
            entidadExistente.setNivel(dto.getNivel());
        }

        NivelEntity actualizado = nivelRepository.save(entidadExistente);

        NivelDTO respuestaDTO = new NivelDTO();
        respuestaDTO.setIdNivel(actualizado.getIdNivel());
        respuestaDTO.setNivel(actualizado.getNivel());
        return respuestaDTO;
    }

    @Override
    @Transactional
    public boolean eliminar2(Long id) {
        if (nivelRepository.existsById(id)) {
            nivelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}