package VisitasITR.API_PTC.Estudiante.Services.impl;

import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;
import VisitasITR.API_PTC.Detalle_Grado.Reposity.DetalleGradoRepository;
import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.Estudiante.Services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final DetalleGradoRepository detalleGradoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteEntity> listarTodos() {
        return estudianteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteEntity buscarPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public EstudianteEntity guardar(EstudianteDTO dto) {
        DetalleGradoEntity detalleGrado = detalleGradoRepository.findById(dto.getIdDetalleGrado())
                .orElseThrow(() -> new RuntimeException("Detalle de grado asociado no encontrado"));

        EstudianteEntity estudiante = EstudianteEntity.builder()
                .nie(dto.getNie())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .detalleGrado(detalleGrado)
                .build();

        return estudianteRepository.save(estudiante);
    }

    @Override
    @Transactional
    public EstudianteEntity actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = buscarPorId(id);

        DetalleGradoEntity detalleGrado = detalleGradoRepository.findById(dto.getIdDetalleGrado())
                .orElseThrow(() -> new RuntimeException("Detalle de grado asociado no encontrado"));

        estudiante.setNie(dto.getNie());
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setDetalleGrado(detalleGrado);

        return estudianteRepository.save(estudiante);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        EstudianteEntity estudiante = buscarPorId(id);
        estudianteRepository.delete(estudiante);
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO dto) {
        EstudianteEntity entidadExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entidadExistente.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            entidadExistente.setApellido(dto.getApellido());
        }
        if (dto.getNie() != null && !dto.getNie().isBlank()) {
            entidadExistente.setNie(dto.getNie());
        }

        EstudianteEntity actualizado = estudianteRepository.save(entidadExistente);

        EstudianteDTO respuestaDTO = new EstudianteDTO();
        respuestaDTO.setIdEstudiante(actualizado.getIdEstudiante());
        respuestaDTO.setNombre(actualizado.getNombre());
        respuestaDTO.setApellido(actualizado.getApellido());
        respuestaDTO.setNie(actualizado.getNie());
        return respuestaDTO;
    }

    @Override
    public boolean eliminar2(Long id) {
        return false;
    }
}