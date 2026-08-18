package VisitasITR.API_PTC.Estudiante_Encargado.Services;

import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.EstudianteEncargado.DTO.EstudianteEncargadoDTO;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteEncargadoService {

    @Autowired
    private EstudianteEncargadoRepository estudianteEncargadoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EncargadoRepository encargadoRepository;

    @Transactional(readOnly = true)
    public List<EstudianteEncargadoDTO> obtenerTodos() {
        return estudianteEncargadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstudianteEncargadoDTO obtenerPorId(Long id) {
        EstudianteEncargadoEntity entity = estudianteEncargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asociación no encontrada con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public EstudianteEncargadoDTO guardar(EstudianteEncargadoDTO dto) {
        EstudianteEntity estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + dto.getIdEstudiante()));

        EncargadoEntity encargado = encargadoRepository.findById(dto.getIdEncargado())
                .orElseThrow(() -> new RuntimeException("Encargado no encontrado con ID: " + dto.getIdEncargado()));

        EstudianteEncargadoEntity entity = EstudianteEncargadoEntity.builder()
                .estudiante(estudiante)
                .encargado(encargado)
                .build();

        return convertirADTO(estudianteEncargadoRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!estudianteEncargadoRepository.existsById(id)) {
            throw new RuntimeException("Asociación no encontrada con ID: " + id);
        }
        estudianteEncargadoRepository.deleteById(id);
    }

    private EstudianteEncargadoDTO convertirADTO(EstudianteEncargadoEntity entity) {
        return EstudianteEncargadoDTO.builder()
                .idEstudianteEncargado(entity.getIdEstudianteEncargado())
                .idEstudiante(entity.getEstudiante().getIdEstudiante())
                .nombreEstudiante(entity.getEstudiante().getEstNombre() + " " + entity.getEstudiante().getEstApellido())
                .idEncargado(entity.getEncargado().getIdEncargado())
                .nombreEncargado(entity.getEncargado().getNombre() + " " + entity.getEncargado().getApellido())
                .build();
    }
}