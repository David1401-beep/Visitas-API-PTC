package VisitasITR.API_PTC.Materia_Docente.Services;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import VisitasITR.API_PTC.Materia_Docente.Repository.MateriaDocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
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
        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada"));
        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado"));

        validarTipo(docente, materia);

        MateriaDocenteEntity entity = MateriaDocenteEntity.builder()
                .materia(materia)
                .docente(docente)
                .build();
        return toDTO(repository.save(entity));
    }

    // El docente tecnico solo da materias tecnicas y el academico solo
    // academicas: no tiene sentido que un docente tecnico de matematicas.
    private void validarTipo(DocenteEntity docente, MateriaEntity materia) {
        String tipoDocente = sinTildes(docente.getDocTipo());
        String tipoMateria = sinTildes(materia.getMatTipo());

        boolean coincide = (tipoDocente.contains("TECNICO") && tipoMateria.equals("TECNICA"))
                || (tipoDocente.contains("ACADEMICO") && tipoMateria.equals("ACADEMICA"));

        if (!coincide) {
            String esperado = tipoDocente.contains("TECNICO") ? "técnicas" : "académicas";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    docente.getDocNombre() + " " + docente.getDocApellido() + " es "
                            + docente.getDocTipo().toLowerCase() + ": solo se le pueden asignar materias " + esperado + ".");
        }
    }

    private String sinTildes(String texto) {
        return Normalizer.normalize(String.valueOf(texto), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase();
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