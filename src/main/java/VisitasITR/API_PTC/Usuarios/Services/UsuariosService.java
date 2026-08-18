package VisitasITR.API_PTC.Usuarios.Services;

import VisitasITR.API_PTC.Usuarios.DTO.UsuariosDTO;
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
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

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