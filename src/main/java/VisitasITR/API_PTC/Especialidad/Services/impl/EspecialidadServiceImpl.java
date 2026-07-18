package VisitasITR.API_PTC.Especialidad.Services.impl;

import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;
import VisitasITR.API_PTC.Especialidad.Reposity.EspecialidadRepository;
import VisitasITR.API_PTC.Especialidad.Services.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadEntity> listarTodos() {
        return especialidadRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public EspecialidadEntity buscarPorId(Long id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public EspecialidadEntity guardar(EspecialidadDTO dto) {
        EspecialidadEntity especialidad = EspecialidadEntity.builder()
                .especialidad(dto.getEspecialidad())
                .build();
        return especialidadRepository.save(especialidad);
    }

    @Override
    @Transactional
    public EspecialidadEntity actualizar(Long id, EspecialidadDTO dto) {
        EspecialidadEntity especialidad = buscarPorId(id);
        especialidad.setEspecialidad(dto.getEspecialidad());
        return especialidadRepository.save(especialidad);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        EspecialidadEntity especialidad = buscarPorId(id);
        especialidadRepository.delete(especialidad);
    }
}