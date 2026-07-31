package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Reposity.CitaReunionRepository;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaReunionService {

    private final CitaReunionRepository citaReunionRepository;
    private final DocenteRepository docenteRepository;
    private final EstudianteEncargadoRepository estudianteEncargadoRepository;

    @Transactional(readOnly = true)
    public List<CitaReunionEntity> listarTodos() {
        return citaReunionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CitaReunionEntity> listarPorDocente(Long idDocente) {
        buscarDocente(idDocente);
        return citaReunionRepository.findByDocente_IdDocente(idDocente);
    }

    @Transactional(readOnly = true)
    public List<CitaReunionEntity> listarPorEstudianteEncargado(Long idRelacion) {
        buscarRelacion(idRelacion);
        return citaReunionRepository
                .findByEstudianteEncargado_IdEstudianteEncargado(idRelacion);
    }

    @Transactional(readOnly = true)
    public CitaReunionEntity buscarPorId(Long id) {
        return citaReunionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }

    @Transactional
    public CitaReunionEntity guardar(CitaReunionDTO dto) {
        validarEstado(dto.getEstado());
        CitaReunionEntity cita = CitaReunionEntity.builder()
                .docente(buscarDocente(dto.getIdDocente()))
                .estudianteEncargado(buscarRelacion(dto.getIdEstudianteEncargado()))
                .motivo(dto.getMotivo())
                .estado(dto.getEstado())
                .observaciones(dto.getObservaciones())
                .fechaReunion(dto.getFechaReunion())
                .build();

        return citaReunionRepository.save(cita);
    }

    @Transactional
    public CitaReunionEntity actualizar(Long id, CitaReunionDTO dto) {
        CitaReunionEntity cita = buscarPorId(id);
        validarEstado(dto.getEstado());
        cita.setDocente(buscarDocente(dto.getIdDocente()));
        cita.setEstudianteEncargado(buscarRelacion(dto.getIdEstudianteEncargado()));
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(dto.getEstado());
        cita.setObservaciones(dto.getObservaciones());
        cita.setFechaReunion(dto.getFechaReunion());
        return citaReunionRepository.save(cita);
    }

    @Transactional
    public CitaReunionDTO actualizarParcial(Long id, CitaReunionDTO dto) {
        CitaReunionEntity cita = buscarPorId(id);

        if (dto.getIdDocente() != null) {
            cita.setDocente(buscarDocente(dto.getIdDocente()));
        }
        if (dto.getIdEstudianteEncargado() != null) {
            cita.setEstudianteEncargado(buscarRelacion(dto.getIdEstudianteEncargado()));
        }
        if (dto.getMotivo() != null) {
            cita.setMotivo(dto.getMotivo());
        }
        if (dto.getEstado() != null) {
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
    public boolean eliminar(Long id) {
        if (!citaReunionRepository.existsById(id)) {
            return false;
        }
        citaReunionRepository.deleteById(id);
        return true;
    }

    private DocenteEntity buscarDocente(Long idDocente) {
        return docenteRepository.findById(idDocente)
                .orElseThrow(() -> new RuntimeException(
                        "Docente no encontrado con ID: " + idDocente
                ));
    }

    private void validarEstado(String estado) {
        if (estado != null && !List.of(
                "PENDIENTE",
                "ACEPTADA",
                "RECHAZADA",
                "CANCELADA",
                "FINALIZADA"
        ).contains(estado)) {
            throw new RuntimeException("El estado de la cita no está permitido");
        }
    }

    private EstudianteEncargadoEntity buscarRelacion(Long idRelacion) {
        return estudianteEncargadoRepository.findById(idRelacion)
                .orElseThrow(() -> new RuntimeException(
                        "Relación estudiante-encargado no encontrada con ID: " + idRelacion
                ));
    }

    private CitaReunionDTO convertirADto(CitaReunionEntity cita) {
        return CitaReunionDTO.builder()
                .idCita(cita.getIdCita())
                .idDocente(cita.getDocente().getIdDocente())
                .idEstudianteEncargado(
                        cita.getEstudianteEncargado().getIdEstudianteEncargado()
                )
                .motivo(cita.getMotivo())
                .estado(cita.getEstado())
                .observaciones(cita.getObservaciones())
                .fechaReunion(cita.getFechaReunion())
                .build();
    }
}
