package VisitasITR.API_PTC.Administrador.Services;

import VisitasITR.API_PTC.Administrador.DTO.AdministradorDTO;
import VisitasITR.API_PTC.Administrador.Entity.AdministradorEntity;
import VisitasITR.API_PTC.Administrador.Repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministradorServices {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AdministradorDTO> obtenerTodos() {
        return administradorRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public AdministradorDTO obtenerPorId(Long id) {
        AdministradorEntity entity = administradorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Administrador no encontrado con ID: " + id
                ));
        return convertirADTO(entity);
    }

    @Transactional
    public AdministradorDTO crear(AdministradorDTO dto) {
        if (administradorRepository.existsByAdmCorreo(dto.getAdmCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo " + dto.getAdmCorreo() + " ya está registrado."
            );
        }

        if (dto.getAdmPassword() == null || dto.getAdmPassword().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contraseña del administrador es obligatoria."
            );
        }

        AdministradorEntity entity = AdministradorEntity.builder()
                .admNombre(dto.getAdmNombre())
                .admApellido(dto.getAdmApellido())
                .admCorreo(dto.getAdmCorreo())
                .admPassword(passwordEncoder.encode(dto.getAdmPassword()))
                .admRol(
                        dto.getAdmRol() != null && !dto.getAdmRol().isBlank()
                                ? dto.getAdmRol()
                                : "ADMINISTRADOR"
                )
                .build();

        return convertirADTO(administradorRepository.save(entity));
    }

    @Transactional
    public AdministradorDTO actualizar(Long id, AdministradorDTO dto) {
        AdministradorEntity entity = administradorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Administrador no encontrado con ID: " + id
                ));

        if (administradorRepository.existsByAdmCorreoAndIdAdministradorNot(
                dto.getAdmCorreo(),
                id
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo " + dto.getAdmCorreo()
                            + " ya está registrado por otro usuario."
            );
        }

        entity.setAdmNombre(dto.getAdmNombre());
        entity.setAdmApellido(dto.getAdmApellido());
        entity.setAdmCorreo(dto.getAdmCorreo());

        if (dto.getAdmPassword() != null && !dto.getAdmPassword().isBlank()) {
            entity.setAdmPassword(
                    passwordEncoder.encode(dto.getAdmPassword())
            );
        }

        if (dto.getAdmRol() != null && !dto.getAdmRol().isBlank()) {
            entity.setAdmRol(dto.getAdmRol());
        }

        return convertirADTO(administradorRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!administradorRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Administrador no encontrado para eliminar con ID: " + id
            );
        }

        administradorRepository.deleteById(id);
    }

    private AdministradorDTO convertirADTO(AdministradorEntity entity) {
        return AdministradorDTO.builder()
                .idAdministrador(entity.getIdAdministrador())
                .admNombre(entity.getAdmNombre())
                .admApellido(entity.getAdmApellido())
                .admCorreo(entity.getAdmCorreo())
                .admRol(entity.getAdmRol())
                .build();
    }
}