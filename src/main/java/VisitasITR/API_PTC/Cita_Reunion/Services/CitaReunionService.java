package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Repository.CitaReunionRepository;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
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
public class CitaReunionService {

    private final CitaReunionRepository citaReunionRepository;
    private final DocenteRepository docenteRepository;
    private final EstudianteEncargadoRepository estudianteEncargadoRepository;

    public List<CitaReunionDTO> obtenerTodas() {
        return citaReunionRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public CitaReunionDTO obtenerPorId(Long id) {
        CitaReunionEntity cita = citaReunionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));
        return convertirADto(cita);
    }

    @Transactional
    public CitaReunionDTO guardar(CitaReunionDTO dto) {
        validarEstado(dto.getEstado());
        DocenteEntity docente = buscarDocente(dto.getIdDocente());
        EstudianteEncargadoEntity rel = buscarRelacion(dto.getIdEstudianteEncargado());

        CitaReunionEntity entity = CitaReunionEntity.builder()
                .docente(docente)
                .estudianteEncargado(rel)
                .motivo(dto.getMotivo())
                .estado(dto.getEstado())
                .observaciones(dto.getObservaciones())
                .fechaReunion(dto.getFechaReunion())
                .build();

        return convertirADto(citaReunionRepository.save(entity));
    }

    @Transactional
    public CitaReunionDTO actualizar(Long id, CitaReunionDTO dto) {
        validarEstado(dto.getEstado());
        CitaReunionEntity cita = citaReunionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));

        DocenteEntity docente = buscarDocente(dto.getIdDocente());
        EstudianteEncargadoEntity rel = buscarRelacion(dto.getIdEstudianteEncargado());

        cita.setDocente(docente);
        cita.setEstudianteEncargado(rel);
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(dto.getEstado());
        cita.setObservaciones(dto.getObservaciones());
        cita.setFechaReunion(dto.getFechaReunion());

        return convertirADto(citaReunionRepository.save(cita));
    }

    @Transactional
    public CitaReunionDTO actualizarParcial(Long id, CitaReunionDTO dto) {
        CitaReunionEntity cita = citaReunionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));

        if (dto.getIdDocente() != null) {
            cita.setDocente(buscarDocente(dto.getIdDocente()));
        }
        if (dto.getIdEstudianteEncargado() != null) {
            cita.setEstudianteEncargado(buscarRelacion(dto.getIdEstudianteEncargado()));
        }
        if (dto.getMotivo() != null && !dto.getMotivo().isBlank()) {
            cita.setMotivo(dto.getMotivo());
        }
        if (dto.getEstado() != null && !dto.getEstado().isBlank()) {
            validarEstado(dto.getEstado());
            cita.setEstado(dto.getEstado());
        }
        if (dto.getObservaciones() != null) {
            cita.setObservaciones(dto.getObservaciones());
        }
        if (dto.getFechaReunion() != null) {
            cita.setFechaReunion(dto.getFechaReunion());
        }

        return convertirADto(citaReunionRepository.save(cita));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!citaReunionRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id);
        }
        citaReunionRepository.deleteById(id);
    }

    private DocenteEntity buscarDocente(Long idDocente) {
        return docenteRepository.findById(idDocente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + idDocente));
    }

    private EstudianteEncargadoEntity buscarRelacion(Long idRelacion) {
        return estudianteEncargadoRepository.findById(idRelacion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Relación estudiante-encargado no encontrada con ID: " + idRelacion));
    }

    private void validarEstado(String estado) {
        if (estado != null && !List.of("PENDIENTE", "ACEPTADA", "RECHAZADA", "CANCELADA", "FINALIZADA").contains(estado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El estado '" + estado + "' no está permitido.");
        }
    }

    private CitaReunionDTO convertirADto(CitaReunionEntity cita) {
        return CitaReunionDTO.builder()
                .idCita(cita.getIdCita())
                .idDocente(cita.getDocente().getIdDocente())
                .idEstudianteEncargado(cita.getEstudianteEncargado().getIdEstudianteEncargado())
                .motivo(cita.getMotivo())
                .estado(cita.getEstado())
                .observaciones(cita.getObservaciones())
                .fechaReunion(cita.getFechaReunion())
                .build();
    }
}