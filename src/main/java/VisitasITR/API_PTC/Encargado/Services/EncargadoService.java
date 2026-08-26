package VisitasITR.API_PTC.Encargado.Services;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
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
public class EncargadoService {

    private final EncargadoRepository repository;

    public List<EncargadoDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EncargadoDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Encargado no encontrado: " + id)));
    }

    @Transactional
    public EncargadoDTO crear(EncargadoDTO dto) {
        if (dto.getEncTelefono() != null && repository.existsByEncTelefono(dto.getEncTelefono())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El teléfono ya está registrado.");
        }
        EncargadoEntity entity = EncargadoEntity.builder()
                .encNombre(dto.getEncNombre())
                .encApellido(dto.getEncApellido())
                .encTelefono(dto.getEncTelefono())
                .encTipo(dto.getEncTipo())
                .build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public EncargadoDTO actualizar(Long id, EncargadoDTO dto) {
        EncargadoEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Encargado no encontrado: " + id));
        entity.setEncNombre(dto.getEncNombre());
        entity.setEncApellido(dto.getEncApellido());
        entity.setEncTelefono(dto.getEncTelefono());
        entity.setEncTipo(dto.getEncTipo());
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Encargado no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private EncargadoDTO toDTO(EncargadoEntity entity) {
        return EncargadoDTO.builder()
                .idEncargado(entity.getIdEncargado())
                .encNombre(entity.getEncNombre())
                .encApellido(entity.getEncApellido())
                .encTelefono(entity.getEncTelefono())
                .encTipo(entity.getEncTipo())
                .build();
    }
}