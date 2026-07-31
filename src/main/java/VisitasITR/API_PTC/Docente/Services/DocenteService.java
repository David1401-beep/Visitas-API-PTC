package VisitasITR.API_PTC.Docente.Services;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocenteService {

    private final DocenteRepository docenteRepository;
    @Transactional(readOnly = true)
    public List<DocenteEntity> listarTodos() {
        return docenteRepository.findAll();
    }
    @Transactional(readOnly = true)
    public DocenteEntity buscarPorId(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + id));
    }
    @Transactional
    public DocenteEntity guardar(DocenteDTO dto) {
        DocenteEntity docente = DocenteEntity.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .clave(dto.getClave())
                .correo(dto.getCorreo())
                .rol(dto.getRol())
                .tipo(dto.getTipo())
                .build();
        return docenteRepository.save(docente);
    }
    @Transactional
    public DocenteEntity actualizar(Long id, DocenteDTO dto) {
        DocenteEntity docente = buscarPorId(id);
        docente.setNombre(dto.getNombre());
        docente.setApellido(dto.getApellido());
        docente.setClave(dto.getClave());
        docente.setCorreo(dto.getCorreo());
        docente.setRol(dto.getRol());
        docente.setTipo(dto.getTipo());
        return docenteRepository.save(docente);
    }
    @Transactional
    public void eliminar(Long id) {
        DocenteEntity docente = buscarPorId(id);
        docenteRepository.delete(docente);
    }
    public DocenteDTO actualizarDocente(Long id, DocenteDTO dto) {
        DocenteEntity entidadExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entidadExistente.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            entidadExistente.setApellido(dto.getApellido());
        }
        if (dto.getClave() != null && !dto.getClave().isBlank()) {
            entidadExistente.setClave(dto.getClave());
        }
        if (dto.getCorreo() != null && !dto.getCorreo().isBlank()) {
            entidadExistente.setCorreo(dto.getCorreo());
        }
        if (dto.getRol() != null && !dto.getRol().isBlank()) {
            entidadExistente.setRol(dto.getRol());
        }
        if (dto.getTipo() != null && !dto.getTipo().isBlank()) {
            entidadExistente.setTipo(dto.getTipo());
        }

        DocenteEntity actualizado = docenteRepository.save(entidadExistente);

        DocenteDTO respuestaDTO = new DocenteDTO();
        respuestaDTO.setIdDocente(actualizado.getIdDocente());
        respuestaDTO.setNombre(actualizado.getNombre());
        respuestaDTO.setApellido(actualizado.getApellido());
        respuestaDTO.setClave(actualizado.getClave());
        respuestaDTO.setCorreo(actualizado.getCorreo());
        respuestaDTO.setRol(actualizado.getRol());
        respuestaDTO.setTipo(actualizado.getTipo());
        return respuestaDTO;
    }
    @Transactional
    public boolean eliminar2(Long id) {
        if (docenteRepository.existsById(id)) {
            docenteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
