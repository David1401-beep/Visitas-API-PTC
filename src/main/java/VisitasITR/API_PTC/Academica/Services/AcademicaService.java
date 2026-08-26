package VisitasITR.API_PTC.Academica.Services;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
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

    public List<AcademicaDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AcademicaDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada: " + id)));
    }

    @Transactional
    public AcademicaDTO crear(AcademicaDTO dto) {
        if (repository.existsByAcademica(dto.getAcademica())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La sección académica ya existe.");
        }
        AcademicaEntity entity = AcademicaEntity.builder().academica(dto.getAcademica()).build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public AcademicaDTO actualizar(Long id, AcademicaDTO dto) {
        AcademicaEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada: " + id));
        entity.setAcademica(dto.getAcademica());
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Académica no encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private AcademicaDTO toDTO(AcademicaEntity entity) {
        return AcademicaDTO.builder().idAcademica(entity.getIdAcademica()).academica(entity.getAcademica()).build();
    }
}