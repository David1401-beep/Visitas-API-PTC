package VisitasITR.API_PTC.Encargado.Services;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncargadoService {

    @Autowired
    private EncargadoRepository encargadoRepository;

    public List<EncargadoDTO> obtenerTodos() {
        return encargadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EncargadoDTO obtenerPorId(Long id) {
        EncargadoEntity entity = encargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    public EncargadoDTO guardar(EncargadoDTO dto) {
        validarTipo(dto.getTipo());
        EncargadoEntity entity = EncargadoEntity.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .telefono(dto.getTelefono())
                .tipo(dto.getTipo().toUpperCase().trim())
                .build();
        return convertirADTO(encargadoRepository.save(entity));
    }

    public EncargadoDTO actualizarParcial(Long id, EncargadoDTO dto) {
        EncargadoEntity entity = encargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entity.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            entity.setApellido(dto.getApellido());
        }
        if (dto.getTelefono() != null && !dto.getTelefono().isBlank()) {
            entity.setTelefono(dto.getTelefono());
        }
        if (dto.getTipo() != null && !dto.getTipo().isBlank()) {
            validarTipo(dto.getTipo());
            entity.setTipo(dto.getTipo().toUpperCase().trim());
        }

        return convertirADTO(encargadoRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!encargadoRepository.existsById(id)) {
            throw new RuntimeException("Encargado no encontrado con ID: " + id);
        }
        encargadoRepository.deleteById(id);
    }

    private void validarTipo(String tipo) {
        if (tipo != null) {
            List<String> tiposValidos = List.of(
                    "PADRE", "MADRE", "HERMANO MAYOR", "HERMANA MAYOR",
                    "TIO", "TIA", "ABUELO", "ABUELA", "TUTOR LEGAL"
            );
            if (!tiposValidos.contains(tipo.toUpperCase().trim())) {
                throw new RuntimeException("El tipo de encargado '" + tipo + "' no es válido");
            }
        }
    }

    private EncargadoDTO convertirADTO(EncargadoEntity entity) {
        return EncargadoDTO.builder()
                .idEncargado(entity.getIdEncargado())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .telefono(entity.getTelefono())
                .tipo(entity.getTipo())
                .build();
    }
}