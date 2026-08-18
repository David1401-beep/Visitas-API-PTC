package VisitasITR.API_PTC.Nivel.Services;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NivelService {

    private final NivelRepository nivelRepository;

    public List<NivelDTO> listarTodos() {
        return nivelRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public NivelDTO buscarPorId(Long id) {
        NivelEntity nivel = nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));
        return convertirADto(nivel);
    }

    @Transactional
    public NivelDTO guardar(NivelDTO dto) {
        NivelEntity nivel = NivelEntity.builder()
                .nivel(dto.getNivel())
                .build();
        return convertirADto(nivelRepository.save(nivel));
    }

    @Transactional
    public NivelDTO actualizar(Long id, NivelDTO dto) {
        NivelEntity nivel = nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));

        nivel.setNivel(dto.getNivel());
        return convertirADto(nivelRepository.save(nivel));
    }

    @Transactional
    public NivelDTO actualizarParcial(Long id, NivelDTO dto) {
        NivelEntity entidadExistente = nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));

        if (dto.getNivel() != null && !dto.getNivel().isBlank()) {
            entidadExistente.setNivel(dto.getNivel());
        }

        return convertirADto(nivelRepository.save(entidadExistente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!nivelRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el nivel para eliminar con ID: " + id);
        }
        nivelRepository.deleteById(id);
    }

    private NivelDTO convertirADto(NivelEntity nivel) {
        return NivelDTO.builder()
                .idNivel(nivel.getIdNivel())
                .nivel(nivel.getNivel())
                .build();
    }
}