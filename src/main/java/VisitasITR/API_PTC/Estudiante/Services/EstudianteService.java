package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Reposity.GradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final AcademicaRepository academicaRepository;
    private final GradoRepository gradoRepository;

    @Transactional(readOnly = true)
    public List<EstudianteEntity> listarTodos() {
        return estudianteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EstudianteEntity buscarPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
    }

    @Transactional
    public EstudianteEntity guardar(EstudianteDTO dto) {
        validarCodigoDisponible(dto.getCodigo(), null);
        AcademicaEntity academica = buscarAcademica(dto.getIdAcademica());
        GradoEntity grado = buscarGrado(dto.getIdGrado());

        EstudianteEntity estudiante = EstudianteEntity.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .grado(dto.getGrado())
                .seccion(dto.getSeccion())
                .especialidad(dto.getEspecialidad())
                .codigo(dto.getCodigo())
                .academica(academica)
                .gradoRelacionado(grado)
                .build();

        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public EstudianteEntity actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = buscarPorId(id);
        validarCodigoDisponible(dto.getCodigo(), id);

        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setGrado(dto.getGrado());
        estudiante.setSeccion(dto.getSeccion());
        estudiante.setEspecialidad(dto.getEspecialidad());
        estudiante.setCodigo(dto.getCodigo());
        estudiante.setAcademica(buscarAcademica(dto.getIdAcademica()));
        estudiante.setGradoRelacionado(buscarGrado(dto.getIdGrado()));

        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = buscarPorId(id);

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            estudiante.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            estudiante.setApellido(dto.getApellido());
        }
        if (dto.getGrado() != null && !dto.getGrado().isBlank()) {
            estudiante.setGrado(dto.getGrado());
        }
        if (dto.getSeccion() != null && !dto.getSeccion().isBlank()) {
            estudiante.setSeccion(dto.getSeccion());
        }
        if (dto.getEspecialidad() != null && !dto.getEspecialidad().isBlank()) {
            estudiante.setEspecialidad(dto.getEspecialidad());
        }
        if (dto.getCodigo() != null && !dto.getCodigo().isBlank()) {
            validarCodigoDisponible(dto.getCodigo(), id);
            estudiante.setCodigo(dto.getCodigo());
        }
        if (dto.getIdAcademica() != null) {
            estudiante.setAcademica(buscarAcademica(dto.getIdAcademica()));
        }
        if (dto.getIdGrado() != null) {
            estudiante.setGradoRelacionado(buscarGrado(dto.getIdGrado()));
        }

        return convertirADto(estudianteRepository.save(estudiante));
    }

    @Transactional
    public boolean eliminar2(Long id) {
        if (!estudianteRepository.existsById(id)) {
            return false;
        }
        estudianteRepository.deleteById(id);
        return true;
    }

    private AcademicaEntity buscarAcademica(Long idAcademica) {
        return academicaRepository.findById(idAcademica)
                .orElseThrow(() -> new RuntimeException(
                        "Sección académica no encontrada con ID: " + idAcademica
                ));
    }

    private GradoEntity buscarGrado(Long idGrado) {
        return gradoRepository.findById(idGrado)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + idGrado));
    }

    private void validarCodigoDisponible(String codigo, Long idEstudianteActual) {
        estudianteRepository.findByCodigo(codigo)
                .filter(estudiante -> !estudiante.getIdEstudiante().equals(idEstudianteActual))
                .ifPresent(estudiante -> {
                    throw new RuntimeException("Ya existe un estudiante con el código: " + codigo);
                });
    }

    private EstudianteDTO convertirADto(EstudianteEntity estudiante) {
        return EstudianteDTO.builder()
                .idEstudiante(estudiante.getIdEstudiante())
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .grado(estudiante.getGrado())
                .seccion(estudiante.getSeccion())
                .especialidad(estudiante.getEspecialidad())
                .codigo(estudiante.getCodigo())
                .idAcademica(estudiante.getAcademica().getIdAcademica())
                .idGrado(estudiante.getGradoRelacionado().getIdGrado())
                .build();
    }
}
