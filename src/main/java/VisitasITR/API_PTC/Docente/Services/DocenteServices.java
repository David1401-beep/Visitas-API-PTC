package VisitasITR.API_PTC.Docente.Services;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
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
public class DocenteServices {

    private final DocenteRepository docenteRepository;
    private final PasswordEncoder passwordEncoder;

    // OBTENER TODOS LOS DOCENTES

    public List<DocenteDTO> obtenerTodos() {
        return docenteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

   //Obtener ID
    public DocenteDTO obtenerPorId(Long id) {

        DocenteEntity entity = docenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Docente no encontrado con ID: " + id
                ));

        return convertirADTO(entity);
    }

    //Crear
    @Transactional
    public DocenteDTO crear(DocenteDTO dto) {

        // Verificar que el correo no esté registrado
        if (docenteRepository.existsByDocCorreo(dto.getDocCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo " + dto.getDocCorreo() + " ya está registrado."
            );
        }

        // La contraseña es obligatoria
        if (dto.getDocPassword() == null || dto.getDocPassword().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La contraseña del docente es obligatoria."
            );
        }

        DocenteEntity entity = DocenteEntity.builder()
                .docNombre(dto.getDocNombre())
                .docApellido(dto.getDocApellido())
                .docClave(dto.getDocClave())
                .docCorreo(dto.getDocCorreo())


                .docPassword(passwordEncoder.encode(dto.getDocPassword()))

                .docTipo(dto.getDocTipo())
                .docRol(
                        dto.getDocRol() != null && !dto.getDocRol().isBlank()
                                ? dto.getDocRol()
                                : "DOCENTE"
                )
                .build();

        return convertirADTO(docenteRepository.save(entity));
    }

  //Actualizar
    @Transactional
    public DocenteDTO actualizar(Long id, DocenteDTO dto) {

        DocenteEntity entity = docenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Docente no encontrado con ID: " + id
                ));

        // Verificar que otro docente no tenga ese correo
        if (docenteRepository.existsByDocCorreoAndIdDocenteNot(
                dto.getDocCorreo(),
                id
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El correo " + dto.getDocCorreo()
                            + " ya está registrado por otro usuario."
            );
        }

        entity.setDocNombre(dto.getDocNombre());
        entity.setDocApellido(dto.getDocApellido());
        entity.setDocClave(dto.getDocClave());
        entity.setDocCorreo(dto.getDocCorreo());
        entity.setDocTipo(dto.getDocTipo());

        // Si manda una contraseña nueva, se cifra automáticamente
        if (dto.getDocPassword() != null &&
                !dto.getDocPassword().isBlank()) {

            entity.setDocPassword(
                    passwordEncoder.encode(dto.getDocPassword())
            );
        }

        if (dto.getDocRol() != null &&
                !dto.getDocRol().isBlank()) {

            entity.setDocRol(dto.getDocRol());
        }

        return convertirADTO(docenteRepository.save(entity));
    }

  //ELIMINAR
    @Transactional
    public void eliminar(Long id) {

        if (!docenteRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Docente no encontrado para eliminar con ID: " + id
            );
        }

        docenteRepository.deleteById(id);
    }

 //Covertir a DTO
    private DocenteDTO convertirADTO(DocenteEntity entity) {

        return DocenteDTO.builder()
                .idDocente(entity.getIdDocente())
                .docNombre(entity.getDocNombre())
                .docApellido(entity.getDocApellido())
                .docClave(entity.getDocClave())
                .docCorreo(entity.getDocCorreo())
                .docTipo(entity.getDocTipo())
                .docRol(entity.getDocRol())
                .build();
    }
}