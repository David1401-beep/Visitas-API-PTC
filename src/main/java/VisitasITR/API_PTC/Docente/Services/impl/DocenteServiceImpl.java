package VisitasITR.API_PTC.Docente.Services.impl;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Docente.Services.DocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DocenteEntity> listarTodos() {
        return docenteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DocenteEntity buscarPorId(Long id) {
        return docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public DocenteEntity guardar(DocenteDTO dto) {
        DocenteEntity docente = DocenteEntity.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .build();
        return docenteRepository.save(docente);
    }

    @Override
    @Transactional
    public DocenteEntity actualizar(Long id, DocenteDTO dto) {
        DocenteEntity docente = buscarPorId(id);
        docente.setNombre(dto.getNombre());
        docente.setApellido(dto.getApellido());
        return docenteRepository.save(docente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        DocenteEntity docente = buscarPorId(id);
        docenteRepository.delete(docente);
    }

    @Override
    public DocenteDTO actualizarDocente(Long id, DocenteDTO dto) {
        DocenteEntity entidadExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entidadExistente.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            entidadExistente.setApellido(dto.getApellido());
        }

        DocenteEntity actualizado = docenteRepository.save(entidadExistente);

        DocenteDTO respuestaDTO = new DocenteDTO();
        respuestaDTO.setIdDocente(actualizado.getIdDocente());
        respuestaDTO.setNombre(actualizado.getNombre());
        respuestaDTO.setApellido(actualizado.getApellido());
        return respuestaDTO;
    }

    @Override
    public boolean eliminar2(Long id) {
        return false;
    }
}
