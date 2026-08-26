package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.DTO.RespuestaEncargadoDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
<<<<<<< HEAD
import VisitasITR.API_PTC.Cita_Reunion.Repository.CitaReunionRepository;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Repository.EstudianteEncargadoRepository;
=======
import VisitasITR.API_PTC.Cita_Reunion.Reposity.CitaReunionRepository;
import VisitasITR.API_PTC.Empleado.Entity.EmpleadoEntity;
import VisitasITR.API_PTC.Empleado.Repository.EmpleadoRepository;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CitaReunionService {

<<<<<<< HEAD
    private final CitaReunionRepository citaReunionRepository;
    private final DocenteRepository docenteRepository;
=======
    private static final String ESTADO_PENDIENTE = "PENDIENTE";
    private static final String ESTADO_ACEPTADA = "ACEPTADA";
    private static final String ESTADO_POSPUESTA = "POSPUESTA";
    private static final int LIMITE_OBSERVACIONES = 300;

    private static final Set<String> ESTADOS_PERMITIDOS = Set.of(
            ESTADO_PENDIENTE,
            ESTADO_ACEPTADA,
            ESTADO_POSPUESTA,
            "RECHAZADA",
            "CANCELADA",
            "FINALIZADA"
    );

    private final CitaReunionRepository citaReunionRepository;
    private final EmpleadoRepository empleadoRepository;
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    private final EstudianteEncargadoRepository estudianteEncargadoRepository;

    public List<CitaReunionDTO> obtenerTodas() {
        return citaReunionRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public List<CitaReunionDTO> obtenerPorEmpleado(Long idEmpleado) {
        buscarEmpleado(idEmpleado);
        return citaReunionRepository.findAllByEmpleado_IdEmpleadoOrderByFechaReunionDesc(idEmpleado)
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public List<CitaReunionDTO> obtenerPorEstudiantesEncargados(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe enviar al menos un ID de estudiante-encargado."
            );
        }

        List<Long> idsValidos = ids.stream()
                .filter(id -> id != null && id > 0)
                .distinct()
                .toList();

        if (idsValidos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los ID de estudiante-encargado no son válidos."
            );
        }

        return citaReunionRepository
                .findAllByEstudianteEncargado_IdEstudianteEncargadoInOrderByFechaReunionAsc(idsValidos)
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public CitaReunionDTO obtenerPorId(Long id) {
<<<<<<< HEAD
        CitaReunionEntity cita = citaReunionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cita no encontrada con ID: " + id));
        return convertirADto(cita);
=======
        return convertirADto(buscarCita(id));
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    }

    @Transactional
    public CitaReunionDTO guardar(CitaReunionDTO dto) {
<<<<<<< HEAD
        validarEstado(dto.getEstado());
        DocenteEntity docente = buscarDocente(dto.getIdDocente());
        EstudianteEncargadoEntity rel = buscarRelacion(dto.getIdEstudianteEncargado());

        CitaReunionEntity entity = CitaReunionEntity.builder()
                .docente(docente)
                .estudianteEncargado(rel)
                .motivo(dto.getMotivo())
                .estado(dto.getEstado())
                .observaciones(dto.getObservaciones())
=======
        String estado = normalizarYValidarEstado(dto.getEstado());
        validarFechaFutura(dto.getFechaReunion());

        EmpleadoEntity empleado = buscarEmpleado(dto.getIdEmpleado());
        EstudianteEncargadoEntity relacion = buscarRelacion(dto.getIdEstudianteEncargado());

        CitaReunionEntity entity = CitaReunionEntity.builder()
                .empleado(empleado)
                .estudianteEncargado(relacion)
                .motivo(dto.getMotivo().trim())
                .estado(estado)
                .observaciones(limpiarTextoOpcional(dto.getObservaciones()))
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
                .fechaReunion(dto.getFechaReunion())
                .build();

        return convertirADto(citaReunionRepository.save(entity));
    }

    @Transactional
    public CitaReunionDTO actualizar(Long id, CitaReunionDTO dto) {
<<<<<<< HEAD
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
=======
        String estado = normalizarYValidarEstado(dto.getEstado());
        validarFechaFutura(dto.getFechaReunion());

        CitaReunionEntity cita = buscarCita(id);
        EmpleadoEntity empleado = buscarEmpleado(dto.getIdEmpleado());
        EstudianteEncargadoEntity relacion = buscarRelacion(dto.getIdEstudianteEncargado());

        cita.setEmpleado(empleado);
        cita.setEstudianteEncargado(relacion);
        cita.setMotivo(dto.getMotivo().trim());
        cita.setEstado(estado);
        cita.setObservaciones(limpiarTextoOpcional(dto.getObservaciones()));
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
        cita.setFechaReunion(dto.getFechaReunion());

        return convertirADto(citaReunionRepository.save(cita));
    }

    @Transactional
<<<<<<< HEAD
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
=======
    public CitaReunionDTO responderComoEncargado(Long id, RespuestaEncargadoDTO respuesta) {
        CitaReunionEntity cita = buscarCita(id);

        Long idRelacionCita = cita.getEstudianteEncargado().getIdEstudianteEncargado();
        if (!idRelacionCita.equals(respuesta.getIdEstudianteEncargado())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "La convocatoria no pertenece al estudiante y encargado de la sesión."
            );
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
        }

        String nuevoEstado = respuesta.getEstado().trim().toUpperCase(Locale.ROOT);
        if (!Set.of(ESTADO_ACEPTADA, ESTADO_POSPUESTA).contains(nuevoEstado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El encargado solamente puede aceptar o posponer una convocatoria."
            );
        }

        // Hace que repetir accidentalmente la misma petición sea seguro.
        if (nuevoEstado.equals(cita.getEstado())) {
            return convertirADto(cita);
        }

        if (!ESTADO_PENDIENTE.equals(cita.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La convocatoria ya fue respondida y no puede modificarse nuevamente."
            );
        }

        if (ESTADO_POSPUESTA.equals(nuevoEstado)) {
            validarPropuestaReprogramacion(respuesta);
            cita.setFechaReunion(respuesta.getNuevaFechaReunion());
            cita.setObservaciones(agregarMotivoReprogramacion(
                    cita.getObservaciones(),
                    respuesta.getMotivoReprogramacion()
            ));
        }

        cita.setEstado(nuevoEstado);
        return convertirADto(citaReunionRepository.save(cita));
    }

    @Transactional
    public void eliminar(Long id) {
        CitaReunionEntity cita = buscarCita(id);
        citaReunionRepository.delete(cita);
    }

    private CitaReunionEntity buscarCita(Long id) {
        return citaReunionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cita no encontrada con ID: " + id
                ));
    }

<<<<<<< HEAD
    private DocenteEntity buscarDocente(Long idDocente) {
        return docenteRepository.findById(idDocente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + idDocente));
=======
    private EmpleadoEntity buscarEmpleado(Long idEmpleado) {
        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empleado no encontrado con ID: " + idEmpleado
                ));
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    }

    private EstudianteEncargadoEntity buscarRelacion(Long idRelacion) {
        return estudianteEncargadoRepository.findById(idRelacion)
                .orElseThrow(() -> new ResponseStatusException(
<<<<<<< HEAD
                        HttpStatus.NOT_FOUND, "Relación estudiante-encargado no encontrada con ID: " + idRelacion));
    }

    private void validarEstado(String estado) {
        if (estado != null && !List.of("PENDIENTE", "ACEPTADA", "RECHAZADA", "CANCELADA", "FINALIZADA").contains(estado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El estado '" + estado + "' no está permitido.");
=======
                        HttpStatus.NOT_FOUND,
                        "Relación estudiante-encargado no encontrada con ID: " + idRelacion
                ));
    }

    private String normalizarYValidarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El estado de la cita es obligatorio."
            );
        }

        String estadoNormalizado = estado.trim().toUpperCase(Locale.ROOT);
        if (!ESTADOS_PERMITIDOS.contains(estadoNormalizado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El estado de la cita no está permitido."
            );
        }

        return estadoNormalizado;
    }

    private void validarFechaFutura(LocalDateTime fechaReunion) {
        if (fechaReunion == null || !fechaReunion.isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "La fecha y hora de la reunión debe ser futura."
            );
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
        }
    }

    private void validarPropuestaReprogramacion(RespuestaEncargadoDTO respuesta) {
        validarFechaFutura(respuesta.getNuevaFechaReunion());

        if (respuesta.getMotivoReprogramacion() == null ||
                respuesta.getMotivoReprogramacion().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Debe indicar el motivo de la reprogramación."
            );
        }
    }

    private String agregarMotivoReprogramacion(String observaciones, String motivo) {
        String propuesta = "Propuesta de reprogramación: " + motivo.trim();
        String resultado = observaciones == null || observaciones.isBlank()
                ? propuesta
                : observaciones.trim() + " | " + propuesta;

        return resultado.length() <= LIMITE_OBSERVACIONES
                ? resultado
                : resultado.substring(0, LIMITE_OBSERVACIONES);
    }

    private String limpiarTextoOpcional(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }

    private CitaReunionDTO convertirADto(CitaReunionEntity cita) {
        EstudianteEncargadoEntity relacion = cita.getEstudianteEncargado();
        EstudianteEntity estudiante = relacion.getEstudiante();
        EncargadoEntity encargado = relacion.getEncargado();
        EmpleadoEntity empleado = cita.getEmpleado();

        return CitaReunionDTO.builder()
                .idCita(cita.getIdCita())
<<<<<<< HEAD
                .idDocente(cita.getDocente().getIdDocente())
                .idEstudianteEncargado(cita.getEstudianteEncargado().getIdEstudianteEncargado())
=======
                .idEmpleado(empleado.getIdEmpleado())
                .idEstudianteEncargado(relacion.getIdEstudianteEncargado())
                .idEstudiante(estudiante.getIdEstudiante())
                .nombreEstudiante(nombreCompleto(
                        estudiante.getEstNombre(),
                        estudiante.getEstApellido()
                ))
                .nombreEncargado(nombreCompleto(
                        encargado.getNombre(),
                        encargado.getApellido()
                ))
                .nombreEmpleado(nombreCompleto(
                        empleado.getEmpNombre(),
                        empleado.getEmpApellido()
                ))
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
                .motivo(cita.getMotivo())
                .estado(cita.getEstado())
                .observaciones(cita.getObservaciones())
                .fechaReunion(cita.getFechaReunion())
                .build();
    }

    private String nombreCompleto(String nombre, String apellido) {
        return (nombre + " " + apellido).trim();
    }
}
