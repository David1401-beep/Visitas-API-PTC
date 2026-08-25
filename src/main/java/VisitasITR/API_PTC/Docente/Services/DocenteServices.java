package VisitasITR.API_PTC.Docente.Services;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
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
public class DocenteServices {

    private final DocenteRepository docenteRepository;

    public List<DocenteDTO> obtenerTodos() {
        return docenteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public DocenteDTO obtenerPorId(Long id) {
        DocenteEntity entity = docenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public DocenteDTO crear(DocenteDTO dto) {
        if (docenteRepository.existsByDocCorreo(dto.getDocCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El correo " + dto.getDocCorreo() + " ya está registrado.");
        }

        DocenteEntity entity = DocenteEntity.builder()
                .docNombre(dto.getDocNombre())
                .docApellido(dto.getDocApellido())
                .docClave(dto.getDocClave())
                .docCorreo(dto.getDocCorreo())
                .docPassword(dto.getDocPassword() != null ? dto.getDocPassword() : "123456")
                .docTipo(dto.getDocTipo())
                .docRol(dto.getDocRol() != null ? dto.getDocRol() : "DOCENTE")
                .build();

        return convertirADTO(docenteRepository.save(entity));
    }

    @Transactional
    public DocenteDTO actualizar(Long id, DocenteDTO dto) {
        DocenteEntity entity = docenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + id));

        if (docenteRepository.existsByDocCorreoAndIdDocenteNot(dto.getDocCorreo(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El correo " + dto.getDocCorreo() + " ya está registrado por otro usuario.");
        }

        entity.setDocNombre(dto.getDocNombre());
        entity.setDocApellido(dto.getDocApellido());
        entity.setDocClave(dto.getDocClave());
        entity.setDocCorreo(dto.getDocCorreo());
        entity.setDocTipo(dto.getDocTipo());

        if (dto.getDocPassword() != null && !dto.getDocPassword().isBlank()) {
            entity.setDocPassword(dto.getDocPassword());
        }

        if (dto.getDocRol() != null) {
            entity.setDocRol(dto.getDocRol());
        }

        return convertirADTO(docenteRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!docenteRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Docente no encontrado para eliminar con ID: " + id);
        }
        docenteRepository.deleteById(id);
    }

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