package VisitasITR.API_PTC.Materia_Docente.Services;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import VisitasITR.API_PTC.Materia_Docente.Reposity.MateriaDocenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaDocenteService {

    private final MateriaDocenteRepository materiaDocenteRepository;
    private final MateriaRepository materiaRepository;
    private final DocenteRepository docenteRepository;
    @Transactional(readOnly = true)
    public List<MateriaDocenteEntity> listarTodos() {
        return materiaDocenteRepository.findAll();
    }
    @Transactional(readOnly = true)
    public MateriaDocenteEntity buscarPorId(Long id) {
        return materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación materia-docente no encontrada con ID: " + id));
    }
    @Transactional
    public MateriaDocenteEntity guardar(MateriaDocenteDTO dto) {
        validarDocenteDisponible(dto.getIdDocente(), null);
        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        MateriaDocenteEntity relacion = MateriaDocenteEntity.builder()
                .materia(materia)
                .docente(docente)
                .build();

        return materiaDocenteRepository.save(relacion);
    }
    @Transactional
    public MateriaDocenteEntity actualizar(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity relacion = buscarPorId(id);
        validarDocenteDisponible(dto.getIdDocente(), id);

        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        relacion.setMateria(materia);
        relacion.setDocente(docente);

        return materiaDocenteRepository.save(relacion);
    }
    @Transactional
    public void eliminar(Long id) {
        MateriaDocenteEntity relacion = buscarPorId(id);
        materiaDocenteRepository.delete(relacion);
    }
    @Transactional
    public MateriaDocenteDTO actualizarMateriaDocente(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity entidadExistente = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MateriaDocente no encontrada con ID: " + id));

        if (dto.getIdMateria() != null) {
            MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + dto.getIdMateria()));
            entidadExistente.setMateria(materia);
        }
        if (dto.getIdDocente() != null) {
            validarDocenteDisponible(dto.getIdDocente(), id);
            DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                    .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + dto.getIdDocente()));
            entidadExistente.setDocente(docente);
        }

        MateriaDocenteEntity actualizado = materiaDocenteRepository.save(entidadExistente);

        MateriaDocenteDTO respuestaDTO = new MateriaDocenteDTO();
        respuestaDTO.setIdMateriaDocente(actualizado.getIdMateriaDocente());
        if (actualizado.getMateria() != null) {
            respuestaDTO.setIdMateria(actualizado.getMateria().getIdMateria());
        }
        if (actualizado.getDocente() != null) {
            respuestaDTO.setIdDocente(actualizado.getDocente().getIdDocente());
        }
        return respuestaDTO;
    }

    private void validarDocenteDisponible(Long idDocente, Long idRelacionActual) {
        materiaDocenteRepository.findByDocente_IdDocente(idDocente)
                .filter(relacion -> !relacion.getIdMateriaDocente().equals(idRelacionActual))
                .ifPresent(relacion -> {
                    throw new RuntimeException(
                            "El docente ya tiene una asignación en MATERIA_DOCENTE"
                    );
                });
    }
    @Transactional
    public boolean eliminar2(Long id) {
        if (materiaDocenteRepository.existsById(id)) {
            materiaDocenteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
