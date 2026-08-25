package VisitasITR.API_PTC.Grado.Services;

import VisitasITR.API_PTC.Especialidad.Repository.EspecialidadRepository;
import VisitasITR.API_PTC.Grado.DTO.GradoDTO;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Repository.GradoRepository;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
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
public class GradoService {

    private final GradoRepository gradoRepository;
    private final NivelRepository nivelRepository;
    private final SeccionTecnicaRepository seccionTecnicaRepository;
    private final EspecialidadRepository especialidadRepository;

    public List<GradoDTO> listarTodos() {
        return gradoRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public GradoDTO buscarPorId(Long id) {
        GradoEntity grado = gradoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grado no encontrado con ID: " + id));
        return convertirADto(grado);
    }

    @Transactional
    public GradoDTO guardar(GradoDTO dto) {
        String gradoLimpio = dto.getGrado().trim();

        if (gradoRepository.existsByGradoIgnoreCaseAndNivelIdNivel(gradoLimpio, dto.getIdNivel())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El grado '" + gradoLimpio + "' ya existe para el nivel especificado.");
        }

        var nivel = nivelRepository.findById(dto.getIdNivel())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nivel asociado no encontrado con ID: " + dto.getIdNivel()));

        var tecnica = dto.getIdTecnica() != null
                ? seccionTecnicaRepository.findById(dto.getIdTecnica())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección técnica asociada no encontrada con ID: " + dto.getIdTecnica()))
                : null;

        var especialidad = dto.getIdEspecialidad() != null
                ? especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Especialidad asociada no encontrada con ID: " + dto.getIdEspecialidad()))
                : null;

        GradoEntity grado = GradoEntity.builder()
                .grado(gradoLimpio)
                .nivel(nivel)
                .seccionTecnica(tecnica)
                .especialidad(especialidad)
                .build();

        return convertirADto(gradoRepository.save(grado));
    }

    @Transactional
    public GradoDTO actualizar(Long id, GradoDTO dto) {
        GradoEntity grado = gradoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grado no encontrado con ID: " + id));

        String gradoLimpio = dto.getGrado().trim();

        if (gradoRepository.existsByGradoIgnoreCaseAndNivelIdNivelAndIdGradoNot(gradoLimpio, dto.getIdNivel(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El grado '" + gradoLimpio + "' ya existe para el nivel especificado.");
        }

        var nivel = nivelRepository.findById(dto.getIdNivel())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nivel asociado no encontrado con ID: " + dto.getIdNivel()));

        var tecnica = dto.getIdTecnica() != null
                ? seccionTecnicaRepository.findById(dto.getIdTecnica())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección técnica asociada no encontrada con ID: " + dto.getIdTecnica()))
                : null;

        var especialidad = dto.getIdEspecialidad() != null
                ? especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Especialidad asociada no encontrada con ID: " + dto.getIdEspecialidad()))
                : null;

        grado.setGrado(gradoLimpio);
        grado.setNivel(nivel);
        grado.setSeccionTecnica(tecnica);
        grado.setEspecialidad(especialidad);

        return convertirADto(gradoRepository.save(grado));
    }

    @Transactional
    public GradoDTO actualizarGrado(Long id, GradoDTO dto) {
        GradoEntity entidadExistente = gradoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grado no encontrado con ID: " + id));

        String nuevoNombre = dto.getGrado() != null && !dto.getGrado().isBlank()
                ? dto.getGrado().trim()
                : entidadExistente.getGrado();

        Long nuevoNivelId = dto.getIdNivel() != null
                ? dto.getIdNivel()
                : entidadExistente.getNivel().getIdNivel();

        if (gradoRepository.existsByGradoIgnoreCaseAndNivelIdNivelAndIdGradoNot(nuevoNombre, nuevoNivelId, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El grado '" + nuevoNombre + "' ya existe para el nivel especificado.");
        }

        if (dto.getGrado() != null && !dto.getGrado().isBlank()) {
            entidadExistente.setGrado(nuevoNombre);
        }
        if (dto.getIdNivel() != null) {
            entidadExistente.setNivel(nivelRepository.findById(dto.getIdNivel())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Nivel asociado no encontrado con ID: " + dto.getIdNivel())));
        }
        if (dto.getIdTecnica() != null) {
            entidadExistente.setSeccionTecnica(
                    seccionTecnicaRepository.findById(dto.getIdTecnica())
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Sección técnica asociada no encontrada con ID: " + dto.getIdTecnica()))
            );
        }
        if (dto.getIdEspecialidad() != null) {
            entidadExistente.setEspecialidad(
                    especialidadRepository.findById(dto.getIdEspecialidad())
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND, "Especialidad asociada no encontrada con ID: " + dto.getIdEspecialidad()))
            );
        }

        return convertirADto(gradoRepository.save(entidadExistente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!gradoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró el grado para eliminar con ID: " + id);
        }
        gradoRepository.deleteById(id);
    }

    private GradoDTO convertirADto(GradoEntity grado) {
        return GradoDTO.builder()
                .idGrado(grado.getIdGrado())
                .grado(grado.getGrado())
                .idNivel(grado.getNivel().getIdNivel())
                .idTecnica(grado.getSeccionTecnica() != null ? grado.getSeccionTecnica().getIdTecnica() : null)
                .idEspecialidad(grado.getEspecialidad() != null ? grado.getEspecialidad().getIdEspecialidad() : null)
                .build();
    }
}