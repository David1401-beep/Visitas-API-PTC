package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<EstudianteDTO> obtenerTodos() {
        return estudianteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EstudianteDTO obtenerPorId(Long id) {
        EstudianteEntity entity = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    public EstudianteDTO crear(EstudianteDTO dto) {
        if (estudianteRepository.existsByEstNie(dto.getEstNie())) {
            throw new RuntimeException("El NIE ya está registrado.");
        }
        EstudianteEntity entity = convertirAEntity(dto);
        if (entity.getEstEstado() == null) entity.setEstEstado("ACTIVO");

        EstudianteEntity guardado = estudianteRepository.save(entity);
        return convertirADTO(guardado);
    }

    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity entity = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));

        entity.setEstNombres(dto.getEstNombres());
        entity.setEstApellidos(dto.getEstApellidos());
        entity.setEstNie(dto.getEstNie());
        entity.setEstCorreo(dto.getEstCorreo());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        if (dto.getEstEstado() != null) entity.setEstEstado(dto.getEstEstado());

        EstudianteEntity actualizado = estudianteRepository.save(entity);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado con ID: " + id);
        }
        estudianteRepository.deleteById(id);
    }

    private EstudianteDTO convertirADTO(EstudianteEntity entity) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setIdEstudiante(entity.getIdEstudiante());
        dto.setEstNombres(entity.getEstNombres());
        dto.setEstApellidos(entity.getEstApellidos());
        dto.setEstNie(entity.getEstNie());
        dto.setEstCorreo(entity.getEstCorreo());
        dto.setEstGrado(entity.getEstGrado());
        dto.setEstSeccion(entity.getEstSeccion());
        dto.setEstEstado(entity.getEstEstado());
        return dto;
    }

    private EstudianteEntity convertirAEntity(EstudianteDTO dto) {
        EstudianteEntity entity = new EstudianteEntity();
        entity.setIdEstudiante(dto.getIdEstudiante());
        entity.setEstNombres(dto.getEstNombres());
        entity.setEstApellidos(dto.getEstApellidos());
        entity.setEstNie(dto.getEstNie());
        entity.setEstCorreo(dto.getEstCorreo());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        entity.setEstEstado(dto.getEstEstado());
        return entity;
    }
}