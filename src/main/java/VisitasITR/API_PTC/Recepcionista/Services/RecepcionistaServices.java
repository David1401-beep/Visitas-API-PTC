package VisitasITR.API_PTC.Recepcionista.Services;

import VisitasITR.API_PTC.Recepcionista.DTO.RecepcionistaDTO;
import VisitasITR.API_PTC.Recepcionista.Entity.RecepcionistaEntity;
import VisitasITR.API_PTC.Recepcionista.Repository.RecepcionistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecepcionistaServices {

    private final RecepcionistaRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<RecepcionistaDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RecepcionistaDTO obtenerPorId(Long id) {
        RecepcionistaEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recepcionista no encontrado: " + id
                ));
        return toDTO(entity);
    }

    @Transactional
    public RecepcionistaDTO crear(RecepcionistaDTO dto) {
        if (repository.existsByRecCorreo(dto.getRecCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo " + dto.getRecCorreo() + " ya existe."
            );
        }

        if (dto.getRecPassword() == null || dto.getRecPassword().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contraseña del recepcionista es obligatoria."
            );
        }

        RecepcionistaEntity entity = RecepcionistaEntity.builder()
                .recNombre(dto.getRecNombre())
                .recApellido(dto.getRecApellido())
                .recCorreo(dto.getRecCorreo())
                .recPassword(passwordEncoder.encode(dto.getRecPassword()))
                .recRol(
                        dto.getRecRol() != null && !dto.getRecRol().isBlank()
                                ? dto.getRecRol()
                                : "RECEPCIONISTA"
                )
                .build();

        return toDTO(repository.save(entity));
    }

    @Transactional
    public RecepcionistaDTO actualizar(Long id, RecepcionistaDTO dto) {
        RecepcionistaEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recepcionista no encontrado: " + id
                ));

        if (repository.existsByRecCorreoAndIdRecepcionistaNot(dto.getRecCorreo(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo ya está en uso."
            );
        }

        entity.setRecNombre(dto.getRecNombre());
        entity.setRecApellido(dto.getRecApellido());
        entity.setRecCorreo(dto.getRecCorreo());

        if (dto.getRecPassword() != null && !dto.getRecPassword().isBlank()) {
            entity.setRecPassword(passwordEncoder.encode(dto.getRecPassword()));
        }

        if (dto.getRecRol() != null && !dto.getRecRol().isBlank()) {
            entity.setRecRol(dto.getRecRol());
        }

        return toDTO(repository.save(entity));
    }

    @Transactional
    public RecepcionistaDTO patch(Long id, Map<String, Object> updates) {
        RecepcionistaEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recepcionista no encontrado: " + id
                ));

        updates.forEach((key, value) -> {
            switch (key) {
                case "recNombre" -> entity.setRecNombre((String) value);
                case "recApellido" -> entity.setRecApellido((String) value);
                case "recCorreo" -> entity.setRecCorreo((String) value);
                case "recPassword" -> {
                    String password = (String) value;
                    if (password != null && !password.isBlank()) {
                        entity.setRecPassword(passwordEncoder.encode(password));
                    }
                }
                case "recRol" -> entity.setRecRol((String) value);
            }
        });

        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Recepcionista no encontrado: " + id
            );
        }

        repository.deleteById(id);
    }

    private RecepcionistaDTO toDTO(RecepcionistaEntity entity) {
        return RecepcionistaDTO.builder()
                .idRecepcionista(entity.getIdRecepcionista())
                .recNombre(entity.getRecNombre())
                .recApellido(entity.getRecApellido())
                .recCorreo(entity.getRecCorreo())
                .recRol(entity.getRecRol())
                .build();
    }
}