package VisitasITR.API_PTC.Especialidad.Services;

import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;
import VisitasITR.API_PTC.Especialidad.Repository.EspecialidadRepository;
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
public class EspecialidadService {

    private final EspecialidadRepository repository;

    public List<EspecialidadDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EspecialidadDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada: " + id)));
    }

    @Transactional
    public EspecialidadDTO crear(EspecialidadDTO dto) {
        if (repository.existsByEspecialidad(dto.getEspecialidad())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La especialidad ya existe.");
        }
        EspecialidadEntity entity = EspecialidadEntity.builder().especialidad(dto.getEspecialidad()).build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public EspecialidadDTO actualizar(Long id, EspecialidadDTO dto) {
        EspecialidadEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada: " + id));
        entity.setEspecialidad(dto.getEspecialidad());
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private EspecialidadDTO toDTO(EspecialidadEntity entity) {
        return EspecialidadDTO.builder().idEspecialidad(entity.getIdEspecialidad()).especialidad(entity.getEspecialidad()).build();
    }
}