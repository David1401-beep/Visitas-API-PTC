package VisitasITR.API_PTC.Usuarios.Services;

import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import VisitasITR.API_PTC.Usuarios.DTO.InicioSesionEncargadoRequest;
import VisitasITR.API_PTC.Usuarios.DTO.SesionEncargadoDTO;
import VisitasITR.API_PTC.Usuarios.DTO.UsuariosDTO;
import VisitasITR.API_PTC.Usuarios.Entity.UsuariosEntity;
import VisitasITR.API_PTC.Usuarios.Repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuariosService {

    private static final String ROL_PADRE = "PADRE";

    private final UsuariosRepository usuariosRepository;
    private final EstudianteRepository estudianteRepository;
    private final EstudianteEncargadoRepository estudianteEncargadoRepository;

    /**
     * El encargado utiliza las credenciales del usuario asociado al estudiante.
     * Después de validar el usuario, se comprueba la cadena completa:
     * USUARIOS -> ESTUDIANTE -> ESTUDIANTE_ENCARGADO.
     */
    public SesionEncargadoDTO iniciarSesionEncargado(InicioSesionEncargadoRequest request) {
        String correo = request.getCorreoEstudiante().trim();

        UsuariosEntity usuario = usuariosRepository.findByUsuEmailIgnoreCase(correo)
                .orElseThrow(() -> new ResponseStatusException(
                        UNAUTHORIZED,
                        "El correo o la contraseña son incorrectos."
                ));

        // Comparación temporal: al incorporar Spring Security debe sustituirse por BCrypt.
        if (!usuario.getUsuPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(
                    UNAUTHORIZED,
                    "El correo o la contraseña son incorrectos."
            );
        }

        if (!ROL_PADRE.equalsIgnoreCase(usuario.getUsuRol())) {
            throw new ResponseStatusException(
                    FORBIDDEN,
                    "El usuario no tiene el rol PADRE."
            );
        }

        List<EstudianteEntity> estudiantes = estudianteRepository
                .findAllByUsuarioEstudiante_IdUsuario(usuario.getIdUsuario());

        if (estudiantes.isEmpty()) {
            throw new ResponseStatusException(
                    UNPROCESSABLE_ENTITY,
                    "El correo no está asociado a ningún estudiante."
            );
        }

        List<Long> idsEstudiante = estudiantes.stream()
                .map(EstudianteEntity::getIdEstudiante)
                .toList();

        List<EstudianteEncargadoEntity> relaciones = estudianteEncargadoRepository
                .findAllByEstudiante_IdEstudianteIn(idsEstudiante);

        if (relaciones.isEmpty()) {
            throw new ResponseStatusException(
                    UNPROCESSABLE_ENTITY,
                    "El estudiante no tiene un encargado asociado."
            );
        }

        List<Long> idsEstudianteEncargado = relaciones.stream()
                .map(EstudianteEncargadoEntity::getIdEstudianteEncargado)
                .toList();

        return SesionEncargadoDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .correoEstudiante(usuario.getUsuEmail())
                .rol(usuario.getUsuRol())
                .idsEstudiante(idsEstudiante)
                .idsEstudianteEncargado(idsEstudianteEncargado)
                .build();
    }

    public List<UsuariosDTO> listarTodos() {
        return usuariosRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public UsuariosDTO buscarPorId(Long id) {
        UsuariosEntity usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertirADto(usuario);
    }

    @Transactional
    public UsuariosDTO guardar(UsuariosDTO dto) {
        if (usuariosRepository.existsByUsuEmail(dto.getUsuEmail())) {
            throw new RuntimeException("El correo del usuario ya se encuentra registrado.");
        }

        UsuariosEntity usuario = UsuariosEntity.builder()
                .usuEmail(dto.getUsuEmail())
                .usuPassword(dto.getUsuPassword())
                .usuRol(dto.getUsuRol())
                .build();

        return convertirADto(usuariosRepository.save(usuario));
    }

    @Transactional
    public UsuariosDTO actualizar(Long id, UsuariosDTO dto) {
        UsuariosEntity usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setUsuEmail(dto.getUsuEmail());
        usuario.setUsuPassword(dto.getUsuPassword());
        usuario.setUsuRol(dto.getUsuRol());

        return convertirADto(usuariosRepository.save(usuario));
    }

    @Transactional
    public UsuariosDTO actualizarParcial(Long id, UsuariosDTO dto) {
        UsuariosEntity usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        if (dto.getUsuEmail() != null && !dto.getUsuEmail().isBlank()) {
            usuario.setUsuEmail(dto.getUsuEmail());
        }
        if (dto.getUsuPassword() != null && !dto.getUsuPassword().isBlank()) {
            usuario.setUsuPassword(dto.getUsuPassword());
        }
        if (dto.getUsuRol() != null && !dto.getUsuRol().isBlank()) {
            usuario.setUsuRol(dto.getUsuRol());
        }

        return convertirADto(usuariosRepository.save(usuario));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuariosRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el usuario para eliminar con ID: " + id);
        }
        usuariosRepository.deleteById(id);
    }

    private UsuariosDTO convertirADto(UsuariosEntity entidad) {
        return UsuariosDTO.builder()
                .idUsuario(entidad.getIdUsuario())
                .usuEmail(entidad.getUsuEmail())
                .usuPassword(entidad.getUsuPassword())
                .usuRol(entidad.getUsuRol())
                .build();
    }
}
