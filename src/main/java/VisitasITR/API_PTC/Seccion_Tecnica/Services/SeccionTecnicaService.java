package VisitasITR.API_PTC.Seccion_Tecnica.Services;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
import VisitasITR.API_PTC.Seccion_Tecnica.Reposity.SeccionTecnicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeccionTecnicaService {

    private final SeccionTecnicaRepository seccionTecnicaRepository;

    public List<SeccionTecnicaDTO> listarTodos() {
        return seccionTecnicaRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public SeccionTecnicaDTO buscarPorId(Long id) {
        SeccionTecnicaEntity tecnica = seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección técnica no encontrada con ID: " + id));
        return convertirADto(tecnica);
    }

    @Transactional
    public SeccionTecnicaDTO guardar(SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity tecnica = SeccionTecnicaEntity.builder()
                .tecnica(dto.getTecnica())
                .build();
        return convertirADto(seccionTecnicaRepository.save(tecnica));
    }

    @Transactional
    public SeccionTecnicaDTO actualizar(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity tecnica = seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección técnica no encontrada con ID: " + id));

        tecnica.setTecnica(dto.getTecnica());
        return convertirADto(seccionTecnicaRepository.save(tecnica));
    }

    @Transactional
    public SeccionTecnicaDTO actualizarParcial(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity entidadExistente = seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección técnica no encontrada con ID: " + id));

        if (dto.getTecnica() != null && !dto.getTecnica().isBlank()) {
            entidadExistente.setTecnica(dto.getTecnica());
        }

        return convertirADto(seccionTecnicaRepository.save(entidadExistente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!seccionTecnicaRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la sección técnica para eliminar con ID: " + id);
        }
        seccionTecnicaRepository.deleteById(id);
    }

    private SeccionTecnicaDTO convertirADto(SeccionTecnicaEntity entidad) {
        return SeccionTecnicaDTO.builder()
                .idTecnica(entidad.getIdTecnica())
                .tecnica(entidad.getTecnica())
                .build();
    }
}