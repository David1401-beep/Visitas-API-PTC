package VisitasITR.API_PTC.Encargado.Services.impl;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import VisitasITR.API_PTC.Encargado.Services.EncargadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EncargadoServiceImpl implements EncargadoService {

    private final EncargadoRepository encargadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EncargadoEntity> listarTodos() {
        return encargadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public EncargadoEntity buscarPorId(Long id) {
        return encargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public EncargadoEntity guardar(EncargadoDTO dto) {
        EncargadoEntity encargado = EncargadoEntity.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .build();
        return encargadoRepository.save(encargado);
    }

    @Override
    @Transactional
    public EncargadoEntity actualizar(Long id, EncargadoDTO dto) {
        EncargadoEntity encargado = buscarPorId(id);
        encargado.setNombre(dto.getNombre());
        encargado.setApellido(dto.getApellido());
        encargado.setTelefono(dto.getTelefono());
        encargado.setCorreo(dto.getCorreo());
        return encargadoRepository.save(encargado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        EncargadoEntity encargado = buscarPorId(id);
        encargadoRepository.delete(encargado);
    }

    @Override
    public EncargadoDTO actualizarEncargado(Long id, EncargadoDTO dto) {
        EncargadoEntity entidadExistente = encargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entidadExistente.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            entidadExistente.setApellido(dto.getApellido());
        }
        if (dto.getTelefono() != null && !dto.getTelefono().isBlank()) {
            entidadExistente.setTelefono(dto.getTelefono());
        }

        EncargadoEntity actualizado = encargadoRepository.save(entidadExistente);

        EncargadoDTO respuestaDTO = new EncargadoDTO();
        respuestaDTO.setIdEncargado(actualizado.getIdEncargado());
        respuestaDTO.setNombre(actualizado.getNombre());
        respuestaDTO.setApellido(actualizado.getApellido());
        respuestaDTO.setTelefono(actualizado.getTelefono());
        return respuestaDTO;
    }

    @Override
    public boolean eliminar2(Long id) {
        return false;
    }
}