package VisitasITR.API_PTC.Seccion_Tecnica.Services;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
import VisitasITR.API_PTC.Seccion_Tecnica.Reposity.SeccionTecnicaRepository;
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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección técnica no encontrada con ID: " + id));
        return convertirADto(tecnica);
    }

    @Transactional
    public SeccionTecnicaDTO guardar(SeccionTecnicaDTO dto) {
        String tecnicaLimpia = dto.getTecnica().trim().toUpperCase();

        if (seccionTecnicaRepository.existsByTecnicaIgnoreCase(tecnicaLimpia)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La sección técnica '" + tecnicaLimpia + "' ya se encuentra registrada.");
        }

        SeccionTecnicaEntity tecnica = SeccionTecnicaEntity.builder()
                .tecnica(tecnicaLimpia)
                .build();

        return convertirADto(seccionTecnicaRepository.save(tecnica));
    }

    @Transactional
    public SeccionTecnicaDTO actualizar(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity tecnica = seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección técnica no encontrada con ID: " + id));

        String tecnicaLimpia = dto.getTecnica().trim().toUpperCase();

        if (seccionTecnicaRepository.existsByTecnicaIgnoreCaseAndIdTecnicaNot(tecnicaLimpia, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La sección técnica '" + tecnicaLimpia + "' ya existe en otro registro.");
        }

        tecnica.setTecnica(tecnicaLimpia);
        return convertirADto(seccionTecnicaRepository.save(tecnica));
    }

    @Transactional
    public SeccionTecnicaDTO actualizarParcial(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity entidadExistente = seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección técnica no encontrada con ID: " + id));

        if (dto.getTecnica() != null && !dto.getTecnica().isBlank()) {
            String tecnicaLimpia = dto.getTecnica().trim().toUpperCase();

            if (seccionTecnicaRepository.existsByTecnicaIgnoreCaseAndIdTecnicaNot(tecnicaLimpia, id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "La sección técnica '" + tecnicaLimpia + "' ya existe en otro registro.");
            }
            entidadExistente.setTecnica(tecnicaLimpia);
        }

        return convertirADto(seccionTecnicaRepository.save(entidadExistente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!seccionTecnicaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró la sección técnica para eliminar con ID: " + id);
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