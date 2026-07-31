package VisitasITR.API_PTC.Encargado.Services;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EncargadoService {

    private final EncargadoRepository encargadoRepository;

    @Transactional(readOnly = true)
    public List<EncargadoEntity> listarTodos() {
        return encargadoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EncargadoEntity buscarPorId(Long id) {
        return encargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + id));
    }

    @Transactional
    public EncargadoEntity guardar(EncargadoDTO dto) {
        validarUnicos(dto, null);
        EncargadoEntity encargado = EncargadoEntity.builder()
                .idUsuario(dto.getIdUsuario())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .telefono(normalizarTelefono(dto.getTelefono()))
                .build();
        return encargadoRepository.save(encargado);
    }

    @Transactional
    public EncargadoEntity actualizar(Long id, EncargadoDTO dto) {
        EncargadoEntity encargado = buscarPorId(id);
        validarUnicos(dto, id);

        encargado.setIdUsuario(dto.getIdUsuario());
        encargado.setNombre(dto.getNombre());
        encargado.setApellido(dto.getApellido());
        encargado.setTelefono(normalizarTelefono(dto.getTelefono()));
        return encargadoRepository.save(encargado);
    }

    @Transactional
    public EncargadoDTO actualizarEncargado(Long id, EncargadoDTO dto) {
        EncargadoEntity encargado = buscarPorId(id);

        if (dto.getIdUsuario() != null) {
            encargadoRepository.findByIdUsuario(dto.getIdUsuario())
                    .filter(encontrado -> !encontrado.getIdPadre().equals(id))
                    .ifPresent(encontrado -> {
                        throw new RuntimeException("El ID de usuario ya está asignado a otro encargado");
                    });
            encargado.setIdUsuario(dto.getIdUsuario());
        }
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            encargado.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            encargado.setApellido(dto.getApellido());
        }
        if (dto.getTelefono() != null) {
            String telefono = normalizarTelefono(dto.getTelefono());
            if (telefono != null) {
                encargadoRepository.findByTelefono(telefono)
                        .filter(encontrado -> !encontrado.getIdPadre().equals(id))
                        .ifPresent(encontrado -> {
                            throw new RuntimeException("El teléfono ya está asignado a otro encargado");
                        });
            }
            encargado.setTelefono(telefono);
        }

        return convertirADto(encargadoRepository.save(encargado));
    }

    @Transactional
    public boolean eliminar2(Long id) {
        if (!encargadoRepository.existsById(id)) {
            return false;
        }
        encargadoRepository.deleteById(id);
        return true;
    }

    private void validarUnicos(EncargadoDTO dto, Long idActual) {
        encargadoRepository.findByIdUsuario(dto.getIdUsuario())
                .filter(encargado -> !encargado.getIdPadre().equals(idActual))
                .ifPresent(encargado -> {
                    throw new RuntimeException("El ID de usuario ya está asignado a otro encargado");
                });

        String telefono = normalizarTelefono(dto.getTelefono());
        if (telefono != null) {
            encargadoRepository.findByTelefono(telefono)
                    .filter(encargado -> !encargado.getIdPadre().equals(idActual))
                    .ifPresent(encargado -> {
                        throw new RuntimeException("El teléfono ya está asignado a otro encargado");
                    });
        }
    }

    private String normalizarTelefono(String telefono) {
        return telefono == null || telefono.isBlank() ? null : telefono;
    }

    private EncargadoDTO convertirADto(EncargadoEntity encargado) {
        return EncargadoDTO.builder()
                .idPadre(encargado.getIdPadre())
                .idUsuario(encargado.getIdUsuario())
                .nombre(encargado.getNombre())
                .apellido(encargado.getApellido())
                .telefono(encargado.getTelefono())
                .build();
    }
}
