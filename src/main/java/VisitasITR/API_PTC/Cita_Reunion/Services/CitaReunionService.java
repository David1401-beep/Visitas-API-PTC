package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Repository.CitaReunionRepository;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    // Horario de atencion del colegio:
    //   lunes a viernes  8:00 a 16:00
    //   sabado           8:00 a 11:00
    //   domingo          cerrado
    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime CIERRE_ENTRE_SEMANA = LocalTime.of(16, 0);
    private static final LocalTime CIERRE_SABADO = LocalTime.of(11, 0);

    public List<CitaReunionDTO> obtenerTodos() {
        return repository.findAllByOrderByCitFechaReunionDesc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CitaReunionDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunion no encontrada: " + id)));
    }


    public List<CitaReunionDTO> obtenerPorDocente(Long idDocente) {
        if (!docenteRepository.existsById(idDocente)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado: " + idDocente);
        }

        return repository.findByDocente_IdDocenteOrderByCitFechaReunionDesc(idDocente).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CitaReunionDTO> obtenerPorDocenteYEstado(Long idDocente, String estado) {
        return repository
                .findByDocente_IdDocenteAndCitEstadoOrderByCitFechaReunionDesc(idDocente, estado)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CitaReunionDTO> buscar(Long idDocente, String texto) {
        if (texto == null || texto.isBlank()) {
            return obtenerPorDocente(idDocente);
        }

        String busqueda = texto.trim();

        List<CitaReunionDTO> resultados = new ArrayList<>(repository
                .findByDocente_IdDocenteAndCitMotivoContainingIgnoreCaseOrderByCitFechaReunionDesc(
                        idDocente, busqueda)
                .stream()
                .map(this::toDTO)
                .toList());

        List<CitaReunionDTO> porEstudiante = obtenerPorDocente(idDocente).stream()
                .filter(dto -> dto.getNombreEstudiante() != null &&
                        dto.getNombreEstudiante().toLowerCase().contains(busqueda.toLowerCase()))
                // Evita repetir las que ya salieron por el motivo.
                .filter(dto -> resultados.stream()
                        .noneMatch(previo -> previo.getIdCita().equals(dto.getIdCita())))
                .toList();

        resultados.addAll(porEstudiante);
        return resultados;
    }

    @Transactional
    public CitaReunionDTO crear(CitaReunionDTO dto) {
        validarHorario(dto.getCitFechaReunion());

        CitaReunionEntity entity = CitaReunionEntity.builder()
                .docente(buscarDocente(dto.getIdDocente()))
                .estudianteEncargado(buscarRelacion(dto.getIdEstudianteEncargado()))
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
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunion no encontrada: " + id));

        validarHorario(dto.getCitFechaReunion());

        entity.setDocente(buscarDocente(dto.getIdDocente()));
        entity.setEstudianteEncargado(buscarRelacion(dto.getIdEstudianteEncargado()));
        entity.setCitMotivo(dto.getCitMotivo());
        entity.setCitEstado(dto.getCitEstado());
        entity.setCitObservaciones(dto.getCitObservaciones());
        entity.setCitFechaReunion(dto.getCitFechaReunion());

        return toDTO(repository.save(entity));
    }

    @Transactional
    public CitaReunionDTO patchEstado(Long id, Map<String, Object> updates) {
        CitaReunionEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunion no encontrada: " + id));

        if (updates.containsKey("citEstado")) {
            String estado = (String) updates.get("citEstado");
            validarEstado(estado);
            entity.setCitEstado(estado);
        }

        if (updates.containsKey("citObservaciones")) {
            entity.setCitObservaciones((String) updates.get("citObservaciones"));
        }

        if (updates.containsKey("citFechaReunion")) {
            Object fecha = updates.get("citFechaReunion");

            if (fecha != null) {
                LocalDateTime nueva = LocalDateTime.parse(fecha.toString());
                validarHorario(nueva);
                entity.setCitFechaReunion(nueva);
            }
        }

        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunion no encontrada: " + id);
        }

        repository.deleteById(id);
    }

    // Apoyo
    private DocenteEntity buscarDocente(Long idDocente) {
        return docenteRepository.findById(idDocente).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado: " + idDocente));
    }

    private EstudianteEncargadoEntity buscarRelacion(Long idRelacion) {
        return estudianteEncargadoRepository.findById(idRelacion).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Relacion Estudiante-Encargado no encontrada: " + idRelacion));
    }

    private void validarEstado(String estado) {
        List<String> validos = List.of(
                "PENDIENTE", "ACEPTADA", "RECHAZADA",
                "CANCELADA", "FINALIZADA", "POSPUESTA");

        if (estado == null || !validos.contains(estado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Estado no valido: " + estado + ". Use " + String.join(", ", validos));
        }
    }

    // Las citas solo se pueden agendar cuando el colegio esta abierto.
    private void validarHorario(LocalDateTime fechaReunion) {
        if (fechaReunion == null) {
            return;
        }

        DayOfWeek dia = fechaReunion.getDayOfWeek();

        if (dia == DayOfWeek.SUNDAY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Los domingos la institucion esta cerrada.");
        }

        LocalTime apertura = HORA_APERTURA;
        LocalTime cierre = dia == DayOfWeek.SATURDAY ? CIERRE_SABADO : CIERRE_ENTRE_SEMANA;
        LocalTime hora = fechaReunion.toLocalTime();

        if (hora.isBefore(apertura) || hora.isAfter(cierre)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    dia == DayOfWeek.SATURDAY
                            ? "Los sabados se atiende de " + apertura + " a " + cierre + "."
                            : "La hora debe estar entre las " + apertura + " y las " + cierre + ".");
        }
    }


    private CitaReunionDTO toDTO(CitaReunionEntity entity) {
        DocenteEntity docente = entity.getDocente();
        EstudianteEncargadoEntity relacion = entity.getEstudianteEncargado();
        EstudianteEntity estudiante = relacion.getEstudiante();
        EncargadoEntity encargado = relacion.getEncargado();

        return CitaReunionDTO.builder()
                .idCita(entity.getIdCita())
                .idDocente(docente.getIdDocente())
                .nombreDocente(docente.getDocNombre() + " " + docente.getDocApellido())
                .idEstudianteEncargado(relacion.getIdEstudianteEncargado())
                .nombreEstudiante(estudiante.getEstNombre() + " " + estudiante.getEstApellido())
                .nombreEncargado(encargado.getEncNombre() + " " + encargado.getEncApellido())
                .citMotivo(entity.getCitMotivo())
                .citEstado(entity.getCitEstado())
                .citObservaciones(entity.getCitObservaciones())
                .citFechaReunion(entity.getCitFechaReunion())
                .build();
    }

    //Listar paginado
    public Page<CitaReunionDTO> obtenerPaginado(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }
}
