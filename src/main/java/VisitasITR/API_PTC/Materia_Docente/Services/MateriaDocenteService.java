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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateriaDocenteService {

    private final MateriaDocenteRepository materiaDocenteRepository;
    private final MateriaRepository materiaRepository;
    private final DocenteRepository docenteRepository;

    public List<MateriaDocenteDTO> listarTodos() {
        return materiaDocenteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MateriaDocenteDTO buscarPorId(Long id) {
        MateriaDocenteEntity entity = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Registro no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public MateriaDocenteDTO guardar(MateriaDocenteDTO dto) {
        if (materiaDocenteRepository.existsByDocenteIdDocente(dto.getIdDocente())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El docente con ID " + dto.getIdDocente() + " ya tiene una materia asignada.");
        }

        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "La materia con ID " + dto.getIdMateria() + " no existe."));

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El docente con ID " + dto.getIdDocente() + " no existe."));

        MateriaDocenteEntity entity = MateriaDocenteEntity.builder()
                .materia(materia)
                .docente(docente)
                .build();

        return convertirADTO(materiaDocenteRepository.save(entity));
    }

    @Transactional
    public MateriaDocenteDTO actualizar(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity entity = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Registro no encontrado con ID: " + id));

        if (materiaDocenteRepository.existsByDocenteIdDocenteAndIdMateriaDocenteNot(dto.getIdDocente(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El docente con ID " + dto.getIdDocente() + " ya tiene una materia asignada.");
        }

        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada con ID: " + dto.getIdMateria()));

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + dto.getIdDocente()));

        entity.setMateria(materia);
        entity.setDocente(docente);

        return convertirADTO(materiaDocenteRepository.save(entity));
    }

    @Transactional
    public MateriaDocenteDTO actualizarParcial(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity entity = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Registro no encontrado con ID: " + id));

        if (dto.getIdDocente() != null) {
            if (materiaDocenteRepository.existsByDocenteIdDocenteAndIdMateriaDocenteNot(dto.getIdDocente(), id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "El docente con ID " + dto.getIdDocente() + " ya tiene una materia asignada.");
            }
            DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + dto.getIdDocente()));
            entity.setDocente(docente);
        }

        if (dto.getIdMateria() != null) {
            MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Materia no encontrada con ID: " + dto.getIdMateria()));
            entity.setMateria(materia);
        }

        return convertirADTO(materiaDocenteRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!materiaDocenteRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Registro no encontrado para eliminar con ID: " + id);
        }
        materiaDocenteRepository.deleteById(id);
    }

    private MateriaDocenteDTO convertirADTO(MateriaDocenteEntity entity) {
        return MateriaDocenteDTO.builder()
                .idMateriaDocente(entity.getIdMateriaDocente())
                .idMateria(entity.getMateria().getIdMateria())
                .nombreMateria(entity.getMateria().getNombre())
                .idDocente(entity.getDocente().getIdDocente())
                .nombreDocente(entity.getDocente().getDocNombre() + " " + entity.getDocente().getDocApellido())
                .build();
    }
}