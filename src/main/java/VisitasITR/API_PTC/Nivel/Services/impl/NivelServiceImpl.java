package VisitasITR.API_PTC.Nivel.Services.impl;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
import VisitasITR.API_PTC.Nivel.Services.NivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelServiceImpl implements NivelService {

    private final NivelRepository nivelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NivelEntity> listarTodos() {
        return nivelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public NivelEntity buscarPorId(Long id) {
        return nivelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public NivelEntity guardar(NivelDTO dto) {
        NivelEntity nivel = NivelEntity.builder()
                .nivel(dto.getNivel())
                .build();
        return nivelRepository.save(nivel);
    }

    @Override
    @Transactional
    public NivelEntity actualizar(Long id, NivelDTO dto) {
        NivelEntity nivel = buscarPorId(id);
        nivel.setNivel(dto.getNivel());
        return nivelRepository.save(nivel);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        NivelEntity nivel = buscarPorId(id);
        nivelRepository.delete(nivel);
    }
}