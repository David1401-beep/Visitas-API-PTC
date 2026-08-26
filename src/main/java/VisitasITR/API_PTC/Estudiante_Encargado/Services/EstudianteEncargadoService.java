package VisitasITR.API_PTC.Estudiante_Encargado.Services;

import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.DTO.EstudianteEncargadoDTO;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
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
public class EstudianteEncargadoService {

    private final EstudianteEncargadoRepository repository;
    private final EstudianteRepository estudianteRepository;
    private final EncargadoRepository encargadoRepository;

    public List<EstudianteEncargadoDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EstudianteEncargadoDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Asignación no encontrada: " + id)));
    }

    @Transactional
    public EstudianteEncargadoDTO crear(EstudianteEncargadoDTO dto) {
        if (repository.existsByEstudiante_IdEstudianteAndEncargado_IdEncargado(dto.getIdEstudiante(), dto.getIdEncargado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La vinculación estudiante-encargado ya existe.");
        }

        EstudianteEncargadoEntity entity = EstudianteEncargadoEntity.builder()
                .estudiante(estudianteRepository.findById(dto.getIdEstudiante())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado")))
                .encargado(encargadoRepository.findById(dto.getIdEncargado())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Encargado no encontrado")))
                .build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asignación no encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private EstudianteEncargadoDTO toDTO(EstudianteEncargadoEntity entity) {
        return EstudianteEncargadoDTO.builder()
                .idEstudianteEncargado(entity.getIdEstudianteEncargado())
                .idEstudiante(entity.getEstudiante().getIdEstudiante())
                .nombreEstudiante(entity.getEstudiante().getEstNombre() + " " + entity.getEstudiante().getEstApellido())
                .idEncargado(entity.getEncargado().getIdEncargado())
                .nombreEncargado(entity.getEncargado().getEncNombre() + " " + entity.getEncargado().getEncApellido())
                .build();
    }
}