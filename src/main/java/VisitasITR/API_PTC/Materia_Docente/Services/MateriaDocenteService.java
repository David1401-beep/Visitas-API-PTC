package VisitasITR.API_PTC.Materia_Docente.Services;

import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
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
public class MateriaDocenteService {

    private final MateriaDocenteRepository repository;
    private final MateriaRepository materiaRepository;
    private final DocenteRepository docenteRepository;

    public List<MateriaDocenteDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public MateriaDocenteDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Asignación no encontrada: " + id)));
    }

    @Transactional
    public MateriaDocenteDTO crear(MateriaDocenteDTO dto) {
        MateriaDocenteEntity entity = MateriaDocenteEntity.builder()
                .materia(materiaRepository.findById(dto.getIdMateria())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada")))
                .docente(docenteRepository.findById(dto.getIdDocente())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado")))
                .build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asignación no encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private MateriaDocenteDTO toDTO(MateriaDocenteEntity entity) {
        return MateriaDocenteDTO.builder()
                .idMateriaDocente(entity.getIdMateriaDocente())
                .idMateria(entity.getMateria().getIdMateria())
                .nombreMateria(entity.getMateria().getMatNombre())
                .idDocente(entity.getDocente().getIdDocente())
                .nombreDocente(entity.getDocente().getDocNombre() + " " + entity.getDocente().getDocApellido())
                .build();
    }
}