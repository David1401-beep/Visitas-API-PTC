package VisitasITR.API_PTC.Recepcionista.Services;

import VisitasITR.API_PTC.Recepcionista.DTO.RecepcionistaDTO;
import VisitasITR.API_PTC.Recepcionista.Entity.RecepcionistaEntity;
import VisitasITR.API_PTC.Recepcionista.Repository.RecepcionistaRepository;
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
public class RecepcionistaServices {

    private final RecepcionistaRepository recepcionistaRepository;

    public List<RecepcionistaDTO> obtenerTodos() {
        return recepcionistaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public RecepcionistaDTO obtenerPorId(Long id) {
        RecepcionistaEntity entity = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recepcionista no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public RecepcionistaDTO crear(RecepcionistaDTO dto) {
        if (recepcionistaRepository.existsByRecCorreo(dto.getRecCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El correo " + dto.getRecCorreo() + " ya está registrado.");
        }

        RecepcionistaEntity entity = RecepcionistaEntity.builder()
                .recNombre(dto.getRecNombre())
                .recApellido(dto.getRecApellido())
                .recCorreo(dto.getRecCorreo())
                .recPassword(dto.getRecPassword())
                .recRol(dto.getRecRol() != null ? dto.getRecRol() : "RECEPCIONISTA")
                .build();

        return convertirADTO(recepcionistaRepository.save(entity));
    }

    @Transactional
    public RecepcionistaDTO actualizar(Long id, RecepcionistaDTO dto) {
        RecepcionistaEntity entity = recepcionistaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recepcionista no encontrado con ID: " + id));

        if (recepcionistaRepository.existsByRecCorreoAndIdRecepcionistaNot(dto.getRecCorreo(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El correo " + dto.getRecCorreo() + " ya está registrado por otro usuario.");
        }

        entity.setRecNombre(dto.getRecNombre());
        entity.setRecApellido(dto.getRecApellido());
        entity.setRecCorreo(dto.getRecCorreo());

        if (dto.getRecPassword() != null && !dto.getRecPassword().isBlank()) {
            entity.setRecPassword(dto.getRecPassword());
        }

        if (dto.getRecRol() != null) {
            entity.setRecRol(dto.getRecRol());
        }

        return convertirADTO(recepcionistaRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!recepcionistaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Recepcionista no encontrado para eliminar con ID: " + id);
        }
        recepcionistaRepository.deleteById(id);
    }

    private RecepcionistaDTO convertirADTO(RecepcionistaEntity entity) {
        return RecepcionistaDTO.builder()
                .idRecepcionista(entity.getIdRecepcionista())
                .recNombre(entity.getRecNombre())
                .recApellido(entity.getRecApellido())
                .recCorreo(entity.getRecCorreo())
                .recRol(entity.getRecRol())
                .build();
    }
}