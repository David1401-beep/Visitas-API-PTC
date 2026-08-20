package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.Usuarios.Entity.UsuariosEntity;
import VisitasITR.API_PTC.Usuarios.Repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final UsuariosRepository usuariosRepository;

    public List<EstudianteDTO> listarTodos() {
        return estudianteRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public EstudianteDTO buscarPorId(Long id) {
        EstudianteEntity estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
        return convertirADto(estudiante);
    }

    @Transactional
    public EstudianteDTO guardar(EstudianteDTO dto) {
        if (estudianteRepository.existsByEstCodigo(dto.getEstCodigo())) {
            throw new RuntimeException("El código del estudiante ya se encuentra registrado.");
        }

        UsuariosEntity usuario = buscarUsuario(dto.getUsuarioEstudiante());

        EstudianteEntity estudiante = EstudianteEntity.builder()
                .estNombre(dto.getEstNombre())
                .estApellido(dto.getEstApellido())
                .estGrado(dto.getEstGrado())
                .estSeccion(dto.getEstSeccion())
                .estEspecialidad(dto.getEstEspecialidad())
                .estCodigo(dto.getEstCodigo())
                .idAcademica(dto.getIdAcademica())
                .idGrado(dto.getIdGrado())
                .usuarioEstudiante(usuario)
                .build();

        return convertirADto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));

        estudiante.setEstNombre(dto.getEstNombre());
        estudiante.setEstApellido(dto.getEstApellido());
        estudiante.setEstGrado(dto.getEstGrado());
        estudiante.setEstSeccion(dto.getEstSeccion());
        estudiante.setEstEspecialidad(dto.getEstEspecialidad());
        estudiante.setEstCodigo(dto.getEstCodigo());
        estudiante.setIdAcademica(dto.getIdAcademica());
        estudiante.setIdGrado(dto.getIdGrado());
        estudiante.setUsuarioEstudiante(buscarUsuario(dto.getUsuarioEstudiante()));

        return convertirADto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public EstudianteDTO actualizarParcial(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));

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
            estudiante.setEstCodigo(dto.getEstCodigo());
        }
        if (dto.getIdAcademica() != null) {
            estudiante.setIdAcademica(dto.getIdAcademica());
        }
        if (dto.getIdGrado() != null) {
            estudiante.setIdGrado(dto.getIdGrado());
        }
        if (dto.getUsuarioEstudiante() != null) {
            estudiante.setUsuarioEstudiante(buscarUsuario(dto.getUsuarioEstudiante()));
        }

        return convertirADto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el estudiante para eliminar con ID: " + id);
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
                .usuarioEstudiante(entidad.getUsuarioEstudiante().getIdUsuario())
                .build();
    }

    private UsuariosEntity buscarUsuario(Long idUsuario) {
        return usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con ID: " + idUsuario
                ));
    }
}
