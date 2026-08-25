package VisitasITR.API_PTC.Encargado.Services;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncargadoService {

    private final EncargadoRepository encargadoRepository;

    public EncargadoService(EncargadoRepository encargadoRepository) {
        this.encargadoRepository = encargadoRepository;
    }

    @Transactional(readOnly = true)
    public List<EncargadoDTO> obtenerTodos() {
        return encargadoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EncargadoDTO obtenerPorId(Long id) {
        EncargadoEntity entity = encargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public EncargadoDTO crear(EncargadoDTO dto) {
        EncargadoEntity entity = EncargadoEntity.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .telefono(dto.getTelefono())
                .tipo(dto.getTipo())
                .build();

        EncargadoEntity guardado = encargadoRepository.save(entity);
        return convertirADTO(guardado);
    }

    @Transactional
    public EncargadoDTO actualizar(Long id, EncargadoDTO dto) {
        EncargadoEntity entity = encargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + id));

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setTipo(dto.getTipo());

        EncargadoEntity actualizado = encargadoRepository.save(entity);
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!encargadoRepository.existsById(id)) {
            throw new RuntimeException("Encargado no encontrado con ID: " + id);
        }
        encargadoRepository.deleteById(id);
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