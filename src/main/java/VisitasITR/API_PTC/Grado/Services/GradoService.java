package VisitasITR.API_PTC.Grado.Services;

import VisitasITR.API_PTC.Especialidad.Reposity.EspecialidadRepository;
import VisitasITR.API_PTC.Grado.DTO.GradoDTO;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Reposity.GradoRepository;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
import VisitasITR.API_PTC.Seccion_Tecnica.Reposity.SeccionTecnicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));
        return convertirADto(grado);
    }

    @Transactional
    public GradoDTO guardar(GradoDTO dto) {
        var nivel = nivelRepository.findById(dto.getIdNivel())
                .orElseThrow(() -> new RuntimeException("Nivel asociado no encontrado"));

        var tecnica = dto.getIdTecnica() != null
                ? seccionTecnicaRepository.findById(dto.getIdTecnica())
                .orElseThrow(() -> new RuntimeException("Sección técnica asociada no encontrada"))
                : null;

        var especialidad = dto.getIdEspecialidad() != null
                ? especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad asociada no encontrada"))
                : null;

        GradoEntity grado = GradoEntity.builder()
                .grado(dto.getGrado())
                .nivel(nivel)
                .seccionTecnica(tecnica)
                .especialidad(especialidad)
                .build();

        return convertirADto(gradoRepository.save(grado));
    }

    @Transactional
    public GradoDTO actualizar(Long id, GradoDTO dto) {
        GradoEntity grado = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));

        var nivel = nivelRepository.findById(dto.getIdNivel())
                .orElseThrow(() -> new RuntimeException("Nivel asociado no encontrado"));

        var tecnica = dto.getIdTecnica() != null
                ? seccionTecnicaRepository.findById(dto.getIdTecnica())
                .orElseThrow(() -> new RuntimeException("Sección técnica asociada no encontrada"))
                : null;

        var especialidad = dto.getIdEspecialidad() != null
                ? especialidadRepository.findById(dto.getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad asociada no encontrada"))
                : null;

        grado.setGrado(dto.getGrado());
        grado.setNivel(nivel);
        grado.setSeccionTecnica(tecnica);
        grado.setEspecialidad(especialidad);

        return convertirADto(gradoRepository.save(grado));
    }

    @Transactional
    public GradoDTO actualizarGrado(Long id, GradoDTO dto) {
        GradoEntity entidadExistente = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));

        if (dto.getGrado() != null && !dto.getGrado().isBlank()) {
            entidadExistente.setGrado(dto.getGrado());
        }
        if (dto.getIdNivel() != null) {
            entidadExistente.setNivel(nivelRepository.findById(dto.getIdNivel())
                    .orElseThrow(() -> new RuntimeException("Nivel asociado no encontrado")));
        }
        if (dto.getIdTecnica() != null) {
            entidadExistente.setSeccionTecnica(
                    seccionTecnicaRepository.findById(dto.getIdTecnica())
                            .orElseThrow(() -> new RuntimeException("Sección técnica asociada no encontrada"))
            );
        }
        if (dto.getIdEspecialidad() != null) {
            entidadExistente.setEspecialidad(
                    especialidadRepository.findById(dto.getIdEspecialidad())
                            .orElseThrow(() -> new RuntimeException("Especialidad asociada no encontrada"))
            );
        }

        return convertirADto(gradoRepository.save(entidadExistente));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!gradoRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el grado para eliminar con ID: " + id);
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