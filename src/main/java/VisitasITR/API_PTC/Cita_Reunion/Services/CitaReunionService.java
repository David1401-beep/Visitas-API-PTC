package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Reposity.CitaReunionRepository;
import VisitasITR.API_PTC.Empleado.Entity.EmpleadoEntity;
import VisitasITR.API_PTC.Empleado.Repository.EmpleadoRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaReunionService {

    @Autowired
    private CitaReunionRepository citaReunionRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EstudianteEncargadoRepository estudianteEncargadoRepository;

    public List<CitaReunionDTO> obtenerTodas() {
        return citaReunionRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public CitaReunionDTO obtenerPorId(Long id) {
        CitaReunionEntity cita = citaReunionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        return convertirADto(cita);
    }

    public CitaReunionDTO guardar(CitaReunionDTO dto) {
        validarEstado(dto.getEstado());
        EmpleadoEntity empleado = buscarEmpleado(dto.getIdEmpleado());
        EstudianteEncargadoEntity rel = buscarRelacion(dto.getIdEstudianteEncargado());

        CitaReunionEntity entity = CitaReunionEntity.builder()
                .empleado(empleado)
                .estudianteEncargado(rel)
                .motivo(dto.getMotivo())
                .estado(dto.getEstado())
                .observaciones(dto.getObservaciones())
                .fechaReunion(dto.getFechaReunion())
                .build();

        return convertirADto(citaReunionRepository.save(entity));
    }

    public CitaReunionDTO actualizar(Long id, CitaReunionDTO dto) {
        validarEstado(dto.getEstado());
        CitaReunionEntity cita = citaReunionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));

        EmpleadoEntity empleado = buscarEmpleado(dto.getIdEmpleado());
        EstudianteEncargadoEntity rel = buscarRelacion(dto.getIdEstudianteEncargado());

        cita.setEmpleado(empleado);
        cita.setEstudianteEncargado(rel);
        cita.setMotivo(dto.getMotivo());
        cita.setEstado(dto.getEstado());
        cita.setObservaciones(dto.getObservaciones());
        cita.setFechaReunion(dto.getFechaReunion());

        return convertirADto(citaReunionRepository.save(cita));
    }

    public void eliminar(Long id) {
        if (!citaReunionRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaReunionRepository.deleteById(id);
    }

    private EmpleadoEntity buscarEmpleado(Long idEmpleado) {
        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + idEmpleado));
    }

    private EstudianteEncargadoEntity buscarRelacion(Long idRelacion) {
        return estudianteEncargadoRepository.findById(idRelacion)
                .orElseThrow(() -> new RuntimeException("Relación estudiante-encargado no encontrada con ID: " + idRelacion));
    }

    private void validarEstado(String estado) {
        if (estado != null && !List.of("PENDIENTE", "ACEPTADA", "RECHAZADA", "CANCELADA", "FINALIZADA").contains(estado)) {
            throw new RuntimeException("El estado de la cita no está permitido");
        }
    }

    private CitaReunionDTO convertirADto(CitaReunionEntity cita) {
        return CitaReunionDTO.builder()
                .idCita(cita.getIdCita())
                .idEmpleado(cita.getEmpleado().getIdEmpleado())
                .idEstudianteEncargado(cita.getEstudianteEncargado().getIdEstudianteEncargado())
                .motivo(cita.getMotivo())
                .estado(cita.getEstado())
                .observaciones(cita.getObservaciones())
                .fechaReunion(cita.getFechaReunion())
                .build();
    }
}