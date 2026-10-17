package VisitasITR.API_PTC.Academica.Services;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
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
public class AcademicaService {

    private final AcademicaRepository repository;
    private final EstudianteRepository estudianteRepository;

    public List<AcademicaDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AcademicaDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada: " + id)));
    }

    @Transactional
    public AcademicaDTO crear(AcademicaDTO dto) {
        String nombre = normalizar(dto.getAcademica());

        if (repository.existsByAcademica(nombre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La sección académica ya existe.");
        }
        AcademicaEntity entity = AcademicaEntity.builder().academica(nombre).build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public AcademicaDTO actualizar(Long id, AcademicaDTO dto) {
        AcademicaEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada: " + id));
        entity.setAcademica(normalizar(dto.getAcademica()));
        return toDTO(repository.save(entity));
    }

    // Se guarda siempre en mayuscula y sin espacios, para que "b5" y "B5" no
    // queden como dos secciones distintas.
    private String normalizar(String academica) {
        return academica.trim().toUpperCase();
    }

    // Una seccion con estudiantes no se borra: se quedarian sin seccion. Solo
    // se puede eliminar cuando ya nadie la usa.
    @Transactional
    public void eliminar(Long id) {
        AcademicaEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada: " + id));

        long estudiantes = estudianteRepository.countByAcademica_IdAcademica(id);

        if (estudiantes > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar la sección " + entity.getAcademica() + ": tiene "
                            + estudiantes + (estudiantes == 1 ? " estudiante asignado." : " estudiantes asignados."));
        }

        repository.delete(entity);
    }

    private AcademicaDTO toDTO(AcademicaEntity entity) {
        return AcademicaDTO.builder().idAcademica(entity.getIdAcademica()).academica(entity.getAcademica()).build();
    }
}