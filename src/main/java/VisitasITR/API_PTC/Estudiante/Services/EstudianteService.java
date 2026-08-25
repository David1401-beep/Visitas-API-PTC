package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
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
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public List<EstudianteDTO> listarTodos() {
        return estudianteRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public EstudianteDTO buscarPorId(Long id) {
        EstudianteEntity estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Estudiante no encontrado con ID: " + id));
        return convertirADto(estudiante);
    }

    @Transactional
    public EstudianteDTO guardar(EstudianteDTO dto) {
        String codigoLimpio = dto.getEstCodigo().trim();

        if (estudianteRepository.existsByEstCodigoIgnoreCase(codigoLimpio)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El código de estudiante '" + codigoLimpio + "' ya se encuentra registrado.");
        }

        EstudianteEntity estudiante = EstudianteEntity.builder()
                .estNombre(dto.getEstNombre())
                .estApellido(dto.getEstApellido())
                .estGrado(dto.getEstGrado())
                .estSeccion(dto.getEstSeccion())
                .estEspecialidad(dto.getEstEspecialidad())
                .estCodigo(codigoLimpio)
                .idAcademica(dto.getIdAcademica())
                .idGrado(dto.getIdGrado())
                .usuarioEstudiante(dto.getUsuarioEstudiante())
                .build();

        return convertirADto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Estudiante no encontrado con ID: " + id));

        String codigoLimpio = dto.getEstCodigo().trim();

        if (estudianteRepository.existsByEstCodigoIgnoreCaseAndIdEstudianteNot(codigoLimpio, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El código '" + codigoLimpio + "' pertenece a otro estudiante.");
        }

        estudiante.setEstNombre(dto.getEstNombre());
        estudiante.setEstApellido(dto.getEstApellido());
        estudiante.setEstGrado(dto.getEstGrado());
        estudiante.setEstSeccion(dto.getEstSeccion());
        estudiante.setEstEspecialidad(dto.getEstEspecialidad());
        estudiante.setEstCodigo(codigoLimpio);
        estudiante.setIdAcademica(dto.getIdAcademica());
        estudiante.setIdGrado(dto.getIdGrado());
        estudiante.setUsuarioEstudiante(dto.getUsuarioEstudiante());

        return convertirADto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public EstudianteDTO actualizarParcial(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Estudiante no encontrado con ID: " + id));

        if (dto.getEstNombre() != null && !dto.getEstNombre().isBlank()) {
            estudiante.setEstNombre(dto.getEstNombre());
        }
        if (dto.getEstApellido() != null && !dto.getEstApellido().isBlank()) {
            estudiante.setEstApellido(dto.getEstApellido());
        }
        if (dto.getEstGrado() != null && !dto.getEstGrado().isBlank()) {
            estudiante.setEstGrado(dto.getEstGrado());
        }
        if (dto.getEstSeccion() != null && !dto.getEstSeccion().isBlank()) {
            estudiante.setEstSeccion(dto.getEstSeccion());
        }
        if (dto.getEstEspecialidad() != null && !dto.getEstEspecialidad().isBlank()) {
            estudiante.setEstEspecialidad(dto.getEstEspecialidad());
        }
        if (dto.getEstCodigo() != null && !dto.getEstCodigo().isBlank()) {
            String codigoLimpio = dto.getEstCodigo().trim();
            if (estudianteRepository.existsByEstCodigoIgnoreCaseAndIdEstudianteNot(codigoLimpio, id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "El código '" + codigoLimpio + "' ya está registrado en otro estudiante.");
            }
            estudiante.setEstCodigo(codigoLimpio);
        }
        if (dto.getIdAcademica() != null) {
            estudiante.setIdAcademica(dto.getIdAcademica());
        }
        if (dto.getIdGrado() != null) {
            estudiante.setIdGrado(dto.getIdGrado());
        }
        if (dto.getUsuarioEstudiante() != null) {
            estudiante.setUsuarioEstudiante(dto.getUsuarioEstudiante());
        }

        return convertirADto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encontró el estudiante para eliminar con ID: " + id);
        }
        estudianteRepository.deleteById(id);
    }

    private EstudianteDTO convertirADto(EstudianteEntity entidad) {
        return EstudianteDTO.builder()
                .idEstudiante(entidad.getIdEstudiante())
                .estNombre(entidad.getEstNombre())
                .estApellido(entidad.getEstApellido())
                .estGrado(entidad.getEstGrado())
                .estSeccion(entidad.getEstSeccion())
                .estEspecialidad(entidad.getEstEspecialidad())
                .estCodigo(entidad.getEstCodigo())
                .idAcademica(entidad.getIdAcademica())
                .idGrado(entidad.getIdGrado())
                .usuarioEstudiante(entidad.getUsuarioEstudiante())
                .build();
    }
}