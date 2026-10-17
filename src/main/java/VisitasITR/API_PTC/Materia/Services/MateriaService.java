package VisitasITR.API_PTC.Materia.Services;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import VisitasITR.API_PTC.Materia_Docente.Repository.MateriaDocenteRepository;
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
    private final MateriaDocenteRepository materiaDocenteRepository;

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

    // Una materia que algun docente imparte no se borra: primero hay que
    // quitarsela en "Materias por docente".
    @Transactional
    public void eliminar(Long id) {
        MateriaEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada: " + id));

        long docentes = materiaDocenteRepository.countByMateria_IdMateria(id);

        if (docentes > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar la materia " + entity.getMatNombre() + ": la imparte"
                            + (docentes == 1 ? " 1 docente." : "n " + docentes + " docentes.")
                            + " Quítela primero en Materias por docente.");
        }

        repository.delete(entity);
    }

    private MateriaDTO toDTO(MateriaEntity entity) {
        return MateriaDTO.builder()
                .idMateria(entity.getIdMateria())
                .matNombre(entity.getMatNombre())
                .matTipo(entity.getMatTipo())
                .build();
    }
}