package VisitasITR.API_PTC.Nivel.Services;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nivel no encontrado con ID: " + id));
        return convertirADto(nivel);
    }

    @Transactional
    public NivelDTO guardar(NivelDTO dto) {
        String nivelLimpio = dto.getNivel().trim().toUpperCase();

        if (nivelRepository.existsByNivelIgnoreCase(nivelLimpio)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El nivel '" + nivelLimpio + "' ya se encuentra registrado.");
        }

        NivelEntity nivel = NivelEntity.builder()
                .nivel(nivelLimpio)
                .build();

        return convertirADto(nivelRepository.save(nivel));
    }

    @Transactional
    public NivelDTO actualizar(Long id, NivelDTO dto) {
        NivelEntity nivel = nivelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nivel no encontrado con ID: " + id));

        String nivelLimpio = dto.getNivel().trim().toUpperCase();

        if (nivelRepository.existsByNivelIgnoreCaseAndIdNivelNot(nivelLimpio, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El nivel '" + nivelLimpio + "' ya existe en otro registro.");
        }

        nivel.setNivel(nivelLimpio);
        return convertirADto(nivelRepository.save(nivel));
    }

    @Transactional
    public NivelDTO actualizarParcial(Long id, NivelDTO dto) {
        NivelEntity entidadExistente = nivelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nivel no encontrado con ID: " + id));

        if (dto.getNivel() != null && !dto.getNivel().isBlank()) {
            String nivelLimpio = dto.getNivel().trim().toUpperCase();

            if (nivelRepository.existsByNivelIgnoreCaseAndIdNivelNot(nivelLimpio, id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "El nivel '" + nivelLimpio + "' ya existe en otro registro.");
            }
            entidadExistente.setNivel(nivelLimpio);
        }

        return convertirADto(nivelRepository.save(entidadExistente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!nivelRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró el nivel para eliminar con ID: " + id);
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