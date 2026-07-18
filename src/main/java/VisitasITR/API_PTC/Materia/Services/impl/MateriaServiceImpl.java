package VisitasITR.API_PTC.Materia.Services.impl;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import VisitasITR.API_PTC.Materia.Services.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MateriaEntity> listarTodos() {
        return materiaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public MateriaEntity buscarPorId(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public MateriaEntity guardar(MateriaDTO dto) {
        MateriaEntity materia = MateriaEntity.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .build();
        return materiaRepository.save(materia);
    }

    @Override
    @Transactional
    public MateriaEntity actualizar(Long id, MateriaDTO dto) {
        MateriaEntity materia = buscarPorId(id);
        materia.setNombre(dto.getNombre());
        materia.setTipo(dto.getTipo());
        return materiaRepository.save(materia);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        MateriaEntity materia = buscarPorId(id);
        materiaRepository.delete(materia);
    }
}