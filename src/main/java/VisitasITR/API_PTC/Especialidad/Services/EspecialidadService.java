package VisitasITR.API_PTC.Especialidad.Services;

import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;
import VisitasITR.API_PTC.Especialidad.Repository.EspecialidadRepository;
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
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public List<EspecialidadDTO> listarTodos() {
        return especialidadRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public EspecialidadDTO buscarPorId(Long id) {
        EspecialidadEntity especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Especialidad no encontrada con ID: " + id));
        return convertirADto(especialidad);
    }

    @Transactional
    public EspecialidadDTO guardar(EspecialidadDTO dto) {
        String nombreLimpio = dto.getEspecialidad().trim();

        if (especialidadRepository.existsByEspecialidadIgnoreCase(nombreLimpio)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La especialidad '" + nombreLimpio + "' ya existe.");
        }

        EspecialidadEntity especialidad = EspecialidadEntity.builder()
                .especialidad(nombreLimpio)
                .build();

        return convertirADto(especialidadRepository.save(especialidad));
    }

    @Transactional
    public EspecialidadDTO actualizar(Long id, EspecialidadDTO dto) {
        EspecialidadEntity especialidad = especialidadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Especialidad no encontrada con ID: " + id));

        String nombreLimpio = dto.getEspecialidad().trim();

        if (especialidadRepository.existsByEspecialidadIgnoreCaseAndIdEspecialidadNot(nombreLimpio, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La especialidad '" + nombreLimpio + "' ya pertenece a otro registro.");
        }

        especialidad.setEspecialidad(nombreLimpio);
        return convertirADto(especialidadRepository.save(especialidad));
    }

    @Transactional
    public EspecialidadDTO actualizarParcial(Long id, EspecialidadDTO dto) {
        EspecialidadEntity entidadExistente = especialidadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Especialidad no encontrada con ID: " + id));

        if (dto.getEspecialidad() != null && !dto.getEspecialidad().isBlank()) {
            String nombreLimpio = dto.getEspecialidad().trim();

            if (especialidadRepository.existsByEspecialidadIgnoreCaseAndIdEspecialidadNot(nombreLimpio, id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "La especialidad '" + nombreLimpio + "' ya existe.");
            }
            entidadExistente.setEspecialidad(nombreLimpio);
        }

        return convertirADto(especialidadRepository.save(entidadExistente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!especialidadRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró la especialidad para eliminar con ID: " + id);
        }
        especialidadRepository.deleteById(id);
    }

    private EspecialidadDTO convertirADto(EspecialidadEntity entidad) {
        return EspecialidadDTO.builder()
                .idEspecialidad(entidad.getIdEspecialidad())
                .especialidad(entidad.getEspecialidad())
                .build();
    }
}