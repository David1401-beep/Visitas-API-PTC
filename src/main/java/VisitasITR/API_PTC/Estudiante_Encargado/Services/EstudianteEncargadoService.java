package VisitasITR.API_PTC.Estudiante_Encargado.Services;

import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.DTO.EstudianteEncargadoDTO;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Repository.EstudianteEncargadoRepository;
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

    private final EstudianteEncargadoRepository estudianteEncargadoRepository;
    private final EstudianteRepository estudianteRepository;
    private final EncargadoRepository encargadoRepository;

    public List<EstudianteEncargadoDTO> obtenerTodos() {
        return estudianteEncargadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EstudianteEncargadoDTO obtenerPorId(Long id) {
        EstudianteEncargadoEntity entity = estudianteEncargadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Asociación no encontrada con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public EstudianteEncargadoDTO guardar(EstudianteEncargadoDTO dto) {
        if (estudianteEncargadoRepository.existsByEstudianteIdEstudianteAndEncargadoIdEncargado(
                dto.getIdEstudiante(), dto.getIdEncargado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El estudiante ya tiene asignado a este encargado.");
        }

        EstudianteEntity estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Estudiante no encontrado con ID: " + dto.getIdEstudiante()));

        EncargadoEntity encargado = encargadoRepository.findById(dto.getIdEncargado())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Encargado no encontrado con ID: " + dto.getIdEncargado()));

        EstudianteEncargadoEntity entity = EstudianteEncargadoEntity.builder()
                .estudiante(estudiante)
                .encargado(encargado)
                .build();

        return convertirADTO(estudianteEncargadoRepository.save(entity));
    }

    @Transactional
    public EstudianteEncargadoDTO actualizar(Long id, EstudianteEncargadoDTO dto) {
        EstudianteEncargadoEntity entity = estudianteEncargadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Asociación no encontrada con ID: " + id));

        if (estudianteEncargadoRepository.existsByEstudianteIdEstudianteAndEncargadoIdEncargadoAndIdEstudianteEncargadoNot(
                dto.getIdEstudiante(), dto.getIdEncargado(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La asociación entre este estudiante y este encargado ya existe.");
        }

        EstudianteEntity estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Estudiante no encontrado con ID: " + dto.getIdEstudiante()));

        EncargadoEntity encargado = encargadoRepository.findById(dto.getIdEncargado())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Encargado no encontrado con ID: " + dto.getIdEncargado()));

        entity.setEstudiante(estudiante);
        entity.setEncargado(encargado);

        return convertirADTO(estudianteEncargadoRepository.save(entity));
    }

    @Transactional
    public EstudianteEncargadoDTO actualizarParcial(Long id, EstudianteEncargadoDTO dto) {
        EstudianteEncargadoEntity entity = estudianteEncargadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Asociación no encontrada con ID: " + id));

        Long idEstudianteNuevo = dto.getIdEstudiante() != null ? dto.getIdEstudiante() : entity.getEstudiante().getIdEstudiante();
        Long idEncargadoNuevo = dto.getIdEncargado() != null ? dto.getIdEncargado() : entity.getEncargado().getIdEncargado();

        if (estudianteEncargadoRepository.existsByEstudianteIdEstudianteAndEncargadoIdEncargadoAndIdEstudianteEncargadoNot(
                idEstudianteNuevo, idEncargadoNuevo, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La relación entre este estudiante y encargado ya se encuentra registrada.");
        }

        if (dto.getIdEstudiante() != null) {
            EstudianteEntity estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Estudiante no encontrado con ID: " + dto.getIdEstudiante()));
            entity.setEstudiante(estudiante);
        }

        if (dto.getIdEncargado() != null) {
            EncargadoEntity encargado = encargadoRepository.findById(dto.getIdEncargado())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Encargado no encontrado con ID: " + dto.getIdEncargado()));
            entity.setEncargado(encargado);
        }

        return convertirADTO(estudianteEncargadoRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!estudianteEncargadoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Asociación no encontrada para eliminar con ID: " + id);
        }
        estudianteEncargadoRepository.deleteById(id);
    }

    private EstudianteEncargadoDTO convertirADTO(EstudianteEncargadoEntity entity) {
        return EstudianteEncargadoDTO.builder()
                .idEstudianteEncargado(entity.getIdEstudianteEncargado())
                .idEstudiante(entity.getEstudiante().getIdEstudiante())
                .nombreEstudiante(entity.getEstudiante().getEstNombres() + " " + entity.getEstudiante().getEstApellidos())
                .idEncargado(entity.getEncargado().getIdEncargado())
                .nombreEncargado(entity.getEncargado().getNombre() + " " + entity.getEncargado().getApellido())
                .build();
    }
}