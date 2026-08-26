package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
<<<<<<< HEAD
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
=======
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.Usuarios.Entity.UsuariosEntity;
import VisitasITR.API_PTC.Usuarios.Repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

<<<<<<< HEAD
    @Autowired
    private EstudianteRepository estudianteRepository;
=======
    private final EstudianteRepository estudianteRepository;
    private final UsuariosRepository usuariosRepository;
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4

    public List<EstudianteDTO> obtenerTodos() {
        return estudianteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EstudianteDTO obtenerPorId(Long id) {
        EstudianteEntity entity = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    public EstudianteDTO crear(EstudianteDTO dto) {
        if (estudianteRepository.existsByEstNie(dto.getEstNie())) {
            throw new RuntimeException("El NIE ya está registrado.");
        }
        EstudianteEntity entity = convertirAEntity(dto);
        if (entity.getEstEstado() == null) entity.setEstEstado("ACTIVO");

<<<<<<< HEAD
        EstudianteEntity guardado = estudianteRepository.save(entity);
        return convertirADTO(guardado);
=======
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
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    }

    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity entity = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));

<<<<<<< HEAD
        entity.setEstNombres(dto.getEstNombres());
        entity.setEstApellidos(dto.getEstApellidos());
        entity.setEstNie(dto.getEstNie());
        entity.setEstCorreo(dto.getEstCorreo());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        if (dto.getEstEstado() != null) entity.setEstEstado(dto.getEstEstado());
=======
        estudiante.setEstNombre(dto.getEstNombre());
        estudiante.setEstApellido(dto.getEstApellido());
        estudiante.setEstGrado(dto.getEstGrado());
        estudiante.setEstSeccion(dto.getEstSeccion());
        estudiante.setEstEspecialidad(dto.getEstEspecialidad());
        estudiante.setEstCodigo(dto.getEstCodigo());
        estudiante.setIdAcademica(dto.getIdAcademica());
        estudiante.setIdGrado(dto.getIdGrado());
        estudiante.setUsuarioEstudiante(buscarUsuario(dto.getUsuarioEstudiante()));
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4

        EstudianteEntity actualizado = estudianteRepository.save(entity);
        return convertirADTO(actualizado);
    }

<<<<<<< HEAD
=======
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
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado con ID: " + id);
        }
        estudianteRepository.deleteById(id);
    }

<<<<<<< HEAD
    private EstudianteDTO convertirADTO(EstudianteEntity entity) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setIdEstudiante(entity.getIdEstudiante());
        dto.setEstNombres(entity.getEstNombres());
        dto.setEstApellidos(entity.getEstApellidos());
        dto.setEstNie(entity.getEstNie());
        dto.setEstCorreo(entity.getEstCorreo());
        dto.setEstGrado(entity.getEstGrado());
        dto.setEstSeccion(entity.getEstSeccion());
        dto.setEstEstado(entity.getEstEstado());
        return dto;
    }

    private EstudianteEntity convertirAEntity(EstudianteDTO dto) {
        EstudianteEntity entity = new EstudianteEntity();
        entity.setIdEstudiante(dto.getIdEstudiante());
        entity.setEstNombres(dto.getEstNombres());
        entity.setEstApellidos(dto.getEstApellidos());
        entity.setEstNie(dto.getEstNie());
        entity.setEstCorreo(dto.getEstCorreo());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        entity.setEstEstado(dto.getEstEstado());
        return entity;
=======
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
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    }

    private UsuariosEntity buscarUsuario(Long idUsuario) {
        return usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con ID: " + idUsuario
                ));
    }
}
