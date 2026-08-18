package VisitasITR.API_PTC.Empleado.Services;

import VisitasITR.API_PTC.Empleado.DTO.EmpleadoDTO;
import VisitasITR.API_PTC.Empleado.Entity.EmpleadoEntity;
import VisitasITR.API_PTC.Empleado.Repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public List<EmpleadoDTO> listarTodos() {
        return empleadoRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public EmpleadoDTO buscarPorId(Long id) {
        EmpleadoEntity empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
        return convertirADto(empleado);
    }

    @Transactional
    public EmpleadoDTO guardar(EmpleadoDTO dto) {
        if (empleadoRepository.existsByEmpCorreo(dto.getEmpCorreo())) {
            throw new RuntimeException("El correo ya se encuentra registrado.");
        }

        EmpleadoEntity empleado = EmpleadoEntity.builder()
                .empNombre(dto.getEmpNombre())
                .empApellido(dto.getEmpApellido())
                .empClave(dto.getEmpClave())
                .empCorreo(dto.getEmpCorreo())
                .empRol(dto.getEmpRol())
                .usuarioEmpleado(dto.getUsuarioEmpleado())
                .build();

        return convertirADto(empleadoRepository.save(empleado));
    }

    @Transactional
    public EmpleadoDTO actualizar(Long id, EmpleadoDTO dto) {
        EmpleadoEntity empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        empleado.setEmpNombre(dto.getEmpNombre());
        empleado.setEmpApellido(dto.getEmpApellido());
        empleado.setEmpClave(dto.getEmpClave());
        empleado.setEmpCorreo(dto.getEmpCorreo());
        empleado.setEmpRol(dto.getEmpRol());
        empleado.setUsuarioEmpleado(dto.getUsuarioEmpleado());

        return convertirADto(empleadoRepository.save(empleado));
    }

    @Transactional
    public EmpleadoDTO actualizarParcial(Long id, EmpleadoDTO dto) {
        EmpleadoEntity empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));

        if (dto.getEmpNombre() != null && !dto.getEmpNombre().isBlank()) {
            empleado.setEmpNombre(dto.getEmpNombre());
        }
        if (dto.getEmpApellido() != null && !dto.getEmpApellido().isBlank()) {
            empleado.setEmpApellido(dto.getEmpApellido());
        }
        if (dto.getEmpClave() != null && !dto.getEmpClave().isBlank()) {
            empleado.setEmpClave(dto.getEmpClave());
        }
        if (dto.getEmpCorreo() != null && !dto.getEmpCorreo().isBlank()) {
            empleado.setEmpCorreo(dto.getEmpCorreo());
        }
        if (dto.getEmpRol() != null && !dto.getEmpRol().isBlank()) {
            empleado.setEmpRol(dto.getEmpRol());
        }
        if (dto.getUsuarioEmpleado() != null) {
            empleado.setUsuarioEmpleado(dto.getUsuarioEmpleado());
        }

        return convertirADto(empleadoRepository.save(empleado));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el empleado para eliminar con ID: " + id);
        }
        empleadoRepository.deleteById(id);
    }

    private EmpleadoDTO convertirADto(EmpleadoEntity entidad) {
        return EmpleadoDTO.builder()
                .idEmpleado(entidad.getIdEmpleado())
                .empNombre(entidad.getEmpNombre())
                .empApellido(entidad.getEmpApellido())
                .empClave(entidad.getEmpClave())
                .empCorreo(entidad.getEmpCorreo())
                .empRol(entidad.getEmpRol())
                .usuarioEmpleado(entidad.getUsuarioEmpleado())
                .build();
    }
}