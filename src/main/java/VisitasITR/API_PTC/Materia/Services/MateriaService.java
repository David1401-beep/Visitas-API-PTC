package VisitasITR.API_PTC.Materia.Services;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
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
public class MateriaService {

    private final MateriaRepository repository;

    public List<MateriaDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MateriaDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada: " + id)));
    }

    @Transactional
    public MateriaDTO crear(MateriaDTO dto) {
        if (repository.existsByMatNombre(dto.getMatNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La materia ya existe.");
        }
        MateriaEntity entity = MateriaEntity.builder()
                .matNombre(dto.getMatNombre())
                .matTipo(dto.getMatTipo())
                .build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public MateriaDTO actualizar(Long id, MateriaDTO dto) {
        MateriaEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada: " + id));
        entity.setMatNombre(dto.getMatNombre());
        entity.setMatTipo(dto.getMatTipo());
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private MateriaDTO toDTO(MateriaEntity entity) {
        return MateriaDTO.builder()
                .idMateria(entity.getIdMateria())
                .matNombre(entity.getMatNombre())
                .matTipo(entity.getMatTipo())
                .build();
    }
}