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

    private final GradoRepository repository;
    private final NivelRepository nivelRepository;
    private final SeccionTecnicaRepository tecnicaRepository;
    private final EspecialidadRepository especialidadRepository;

    public List<GradoDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public GradoDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Grado no encontrado: " + id)));
    }

    @Transactional
    public GradoDTO crear(GradoDTO dto) {
        GradoEntity entity = GradoEntity.builder()
                .grado(dto.getGrado())
                .nivel(nivelRepository.findById(dto.getIdNivel())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nivel no encontrado")))
                .seccionTecnica(dto.getIdTecnica() != null ? tecnicaRepository.findById(dto.getIdTecnica()).orElse(null) : null)
                .especialidad(dto.getIdEspecialidad() != null ? especialidadRepository.findById(dto.getIdEspecialidad()).orElse(null) : null)
                .build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public GradoDTO actualizar(Long id, GradoDTO dto) {
        GradoEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Grado no encontrado: " + id));

        entity.setGrado(dto.getGrado());
        entity.setNivel(nivelRepository.findById(dto.getIdNivel())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nivel no encontrado")));
        entity.setSeccionTecnica(dto.getIdTecnica() != null ? tecnicaRepository.findById(dto.getIdTecnica()).orElse(null) : null);
        entity.setEspecialidad(dto.getIdEspecialidad() != null ? especialidadRepository.findById(dto.getIdEspecialidad()).orElse(null) : null);

        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grado no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private GradoDTO toDTO(GradoEntity entity) {
        return GradoDTO.builder()
                .idGrado(entity.getIdGrado())
                .grado(entity.getGrado())
                .idNivel(entity.getNivel().getIdNivel())
                .nombreNivel(entity.getNivel().getNivel())
                .idTecnica(entity.getSeccionTecnica() != null ? entity.getSeccionTecnica().getIdTecnica() : null)
                .nombreTecnica(entity.getSeccionTecnica() != null ? entity.getSeccionTecnica().getTecnica() : null)
                .idEspecialidad(entity.getEspecialidad() != null ? entity.getEspecialidad().getIdEspecialidad() : null)
                .nombreEspecialidad(entity.getEspecialidad() != null ? entity.getEspecialidad().getEspecialidad() : null)
                .build();
    }
}