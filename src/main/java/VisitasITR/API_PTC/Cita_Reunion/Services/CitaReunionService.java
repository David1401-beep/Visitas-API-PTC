package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Repository.CitaReunionRepository;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CitaReunionService {

    private final CitaReunionRepository repository;
    private final DocenteRepository docenteRepository;
    private final EstudianteEncargadoRepository estudianteEncargadoRepository;

    public List<CitaReunionDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CitaReunionDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunión no encontrada: " + id)));
    }

    @Transactional
    public CitaReunionDTO crear(CitaReunionDTO dto) {
        CitaReunionEntity entity = CitaReunionEntity.builder()
                .docente(docenteRepository.findById(dto.getIdDocente())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado")))
                .estudianteEncargado(estudianteEncargadoRepository.findById(dto.getIdEstudianteEncargado())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relación Estudiante-Encargado no encontrada")))
                .citMotivo(dto.getCitMotivo())
                .citEstado(dto.getCitEstado() != null ? dto.getCitEstado() : "PENDIENTE")
                .citObservaciones(dto.getCitObservaciones())
                .citFechaReunion(dto.getCitFechaReunion())
                .build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public CitaReunionDTO actualizar(Long id, CitaReunionDTO dto) {
        CitaReunionEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunión no encontrada: " + id));

        entity.setDocente(docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado")));
        entity.setEstudianteEncargado(estudianteEncargadoRepository.findById(dto.getIdEstudianteEncargado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relación Estudiante-Encargado no encontrada")));
        entity.setCitMotivo(dto.getCitMotivo());
        entity.setCitEstado(dto.getCitEstado());
        entity.setCitObservaciones(dto.getCitObservaciones());
        entity.setCitFechaReunion(dto.getCitFechaReunion());

        return toDTO(repository.save(entity));
    }

    @Transactional
    public CitaReunionDTO patchEstado(Long id, Map<String, Object> updates) {
        CitaReunionEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunión no encontrada: " + id));

        if (updates.containsKey("citEstado")) {
            entity.setCitEstado((String) updates.get("citEstado"));
        }
        if (updates.containsKey("citObservaciones")) {
            entity.setCitObservaciones((String) updates.get("citObservaciones"));
        }

        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunión no encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private CitaReunionDTO toDTO(CitaReunionEntity entity) {
        return CitaReunionDTO.builder()
                .idCita(entity.getIdCita())
                .idDocente(entity.getDocente().getIdDocente())
                .nombreDocente(entity.getDocente().getDocNombre() + " " + entity.getDocente().getDocApellido())
                .idEstudianteEncargado(entity.getEstudianteEncargado().getIdEstudianteEncargado())
                .citMotivo(entity.getCitMotivo())
                .citEstado(entity.getCitEstado())
                .citObservaciones(entity.getCitObservaciones())
                .citFechaReunion(entity.getCitFechaReunion())
                .build();
    }
}