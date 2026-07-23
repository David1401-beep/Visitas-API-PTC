package VisitasITR.API_PTC.Materia_Docente.Services.impl;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import VisitasITR.API_PTC.Materia_Docente.Reposity.MateriaDocenteRepository;
import VisitasITR.API_PTC.Materia_Docente.Services.MateriaDocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaDocenteServiceImpl implements MateriaDocenteService {

    private final MateriaDocenteRepository materiaDocenteRepository;
    private final MateriaRepository materiaRepository;
    private final DocenteRepository docenteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MateriaDocenteEntity> listarTodos() {
        return materiaDocenteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MateriaDocenteEntity buscarPorId(Long id) {
        return materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación materia-docente no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public MateriaDocenteEntity guardar(MateriaDocenteDTO dto) {
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

    @Override
    @Transactional
    public MateriaDocenteEntity actualizar(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity relacion = buscarPorId(id);

        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        relacion.setMateria(materia);
        relacion.setDocente(docente);

        return materiaDocenteRepository.save(relacion);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        MateriaDocenteEntity relacion = buscarPorId(id);
        materiaDocenteRepository.delete(relacion);
    }

    @Override
    public MateriaDocenteDTO actualizarMateriaDocente(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity entidadExistente = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MateriaDocente no encontrada con ID: " + id));

        if (dto.getIdMateria() != null) {
            MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + dto.getIdMateria()));
            entidadExistente.setMateria(materia);
        }
        if (dto.getIdDocente() != null) {
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

    @Override
    @Transactional
    public boolean eliminar2(Long id) {
        if (materiaDocenteRepository.existsById(id)) {
            materiaDocenteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}