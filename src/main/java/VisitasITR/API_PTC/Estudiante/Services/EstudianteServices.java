package VisitasITR.API_PTC.Estudiante.Services;

import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
import VisitasITR.API_PTC.Grado.Repository.GradoRepository;
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
public class EstudianteServices {

    private final EstudianteRepository repository;
    private final AcademicaRepository academicaRepository;
    private final GradoRepository gradoRepository;

    public List<EstudianteDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EstudianteDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado: " + id)));
    }

    @Transactional
    public EstudianteDTO crear(EstudianteDTO dto) {
        if (repository.existsByEstCorreo(dto.getEstCorreo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya existe.");
        }
        if (repository.existsByEstCodigo(dto.getEstCodigo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El código de estudiante ya existe.");
        }

        EstudianteEntity entity = EstudianteEntity.builder()
                .estNombre(dto.getEstNombre())
                .estApellido(dto.getEstApellido())
                .estCorreo(dto.getEstCorreo())
                .estPassword(dto.getEstPassword() != null ? dto.getEstPassword() : "123456")
                .estGrado(dto.getEstGrado())
                .estSeccion(dto.getEstSeccion())
                .estEspecialidad(dto.getEstEspecialidad())
                .estCodigo(dto.getEstCodigo())
                .estRol(dto.getEstRol() != null ? dto.getEstRol() : "ESTUDIANTE")
                .academica(academicaRepository.findById(dto.getIdAcademica())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada")))
                .grado(gradoRepository.findById(dto.getIdGrado())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grado no encontrado")))
                .build();

        return toDTO(repository.save(entity));
    }

    @Transactional
    public EstudianteDTO actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado: " + id));

        entity.setEstNombre(dto.getEstNombre());
        entity.setEstApellido(dto.getEstApellido());
        entity.setEstCorreo(dto.getEstCorreo());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        entity.setEstEspecialidad(dto.getEstEspecialidad());
        entity.setEstCodigo(dto.getEstCodigo());
        entity.setAcademica(academicaRepository.findById(dto.getIdAcademica())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada")));
        entity.setGrado(gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grado no encontrado")));

        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private EstudianteDTO toDTO(EstudianteEntity entity) {
        return EstudianteDTO.builder()
                .idEstudiante(entity.getIdEstudiante())
                .estNombre(entity.getEstNombre())
                .estApellido(entity.getEstApellido())
                .estCorreo(entity.getEstCorreo())
                .estGrado(entity.getEstGrado())
                .estSeccion(entity.getEstSeccion())
                .estEspecialidad(entity.getEstEspecialidad())
                .estCodigo(entity.getEstCodigo())
                .estRol(entity.getEstRol())
                .idAcademica(entity.getAcademica().getIdAcademica())
                .nombreAcademica(entity.getAcademica().getAcademica())
                .idGrado(entity.getGrado().getIdGrado())
                .nombreGrado(entity.getGrado().getGrado())
                .build();
    }
}