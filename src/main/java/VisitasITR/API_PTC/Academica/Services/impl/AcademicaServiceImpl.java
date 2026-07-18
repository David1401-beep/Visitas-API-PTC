package VisitasITR.API_PTC.Academica.Services.impl;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Reposity.AcademicaRepository;
import VisitasITR.API_PTC.Academica.Services.AcademicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicaServiceImpl implements AcademicaService {

    private final AcademicaRepository academicaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AcademicaEntity> listarTodos() {
        return academicaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AcademicaEntity buscarPorId(Long id) {
        return academicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección académica no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public AcademicaEntity guardar(AcademicaDTO dto) {
        AcademicaEntity academica = AcademicaEntity.builder()
                .seccion(dto.getSeccion())
                .build();
        return academicaRepository.save(academica);
    }

    @Override
    @Transactional
    public AcademicaEntity actualizar(Long id, AcademicaDTO dto) {
        AcademicaEntity academica = buscarPorId(id);
        academica.setSeccion(dto.getSeccion());
        return academicaRepository.save(academica);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        AcademicaEntity academica = buscarPorId(id);
        academicaRepository.delete(academica);
    }
}
