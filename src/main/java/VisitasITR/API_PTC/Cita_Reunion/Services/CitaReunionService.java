package VisitasITR.API_PTC.Cita_Reunion.Services;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Repository.CitaReunionRepository;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Repository.EstudianteEncargadoRepository;
import VisitasITR.API_PTC.Security.RolesAuth;
import VisitasITR.API_PTC.Security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    //   sabado           cerrado
    //   domingo          cerrado
    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(16, 0);

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
        validarFechaFutura(dto.getCitFechaReunion());

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

        validarAcceso(entity);
        validarHorario(dto.getCitFechaReunion());
        validarCambioDeFecha(entity.getCitFechaReunion(), dto.getCitFechaReunion());

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

        validarAcceso(entity);

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
                validarCambioDeFecha(entity.getCitFechaReunion(), nueva);
                entity.setCitFechaReunion(nueva);
            }
        }

        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        CitaReunionEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita de reunion no encontrada: " + id));

        validarAcceso(entity);

        repository.delete(entity);
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

    // Una cita solo la puede tocar quien participa en ella: el docente al que
    // le toca, o el encargado del estudiante. El administrador y la
    // recepcionista entran a todas porque ese es su trabajo.
    //
    // Sin esto bastaba con tener la sesion abierta para cambiar la cita de
    // cualquier otra persona, porque el id del docente lo pone el navegador.
    private void validarAcceso(CitaReunionEntity cita) {
        UsuarioAutenticado usuario = usuarioActual();

        if (usuario == null || usuario.id() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No fue posible identificar al usuario de la sesion.");
        }

        String rol = RolesAuth.normalizar(usuario.rol());

        if (rol.equals("ADMINISTRADOR") || rol.equals("RECEPCIONISTA")) {
            return;
        }

        // El id del token es el de la tabla de origen: para un docente es su
        // ID_DOCENTE y para un encargado el ID_ESTUDIANTE con el que entro.
        if (rol.startsWith("DOCENTE")
                && usuario.id().equals(cita.getDocente().getIdDocente())) {
            return;
        }

        if (rol.equals("ENCARGADO")
                && usuario.id().equals(
                        cita.getEstudianteEncargado().getEstudiante().getIdEstudiante())) {
            return;
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Esta cita no le pertenece.");
    }

    private UsuarioAutenticado usuarioActual() {
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();

        if (autenticacion == null
                || !(autenticacion.getPrincipal() instanceof UsuarioAutenticado usuario)) {
            return null;
        }

        return usuario;
    }

    // Solo reviso que sea futura cuando la fecha de verdad cambia. Si la
    // dejan igual es porque estan editando otra cosa (el motivo, el estado)
    // y una cita vieja se tiene que poder seguir cerrando.
    private void validarCambioDeFecha(LocalDateTime actual, LocalDateTime nueva) {
        if (nueva == null || nueva.equals(actual)) {
            return;
        }

        validarFechaFutura(nueva);
    }

    private void validarFechaFutura(LocalDateTime fechaReunion) {
        if (fechaReunion == null) {
            return;
        }

        if (fechaReunion.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de la reunion ya paso. Elija una fecha futura.");
        }
    }

    // Las citas solo se pueden agendar cuando el colegio esta abierto.
    private void validarHorario(LocalDateTime fechaReunion) {
        if (fechaReunion == null) {
            return;
        }

        DayOfWeek dia = fechaReunion.getDayOfWeek();

        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Los fines de semana no se atienden reuniones.");
        }

        LocalTime hora = fechaReunion.toLocalTime();

        if (hora.isBefore(HORA_APERTURA) || hora.isAfter(HORA_CIERRE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La hora debe estar entre las " + HORA_APERTURA + " y las " + HORA_CIERRE + ".");
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
