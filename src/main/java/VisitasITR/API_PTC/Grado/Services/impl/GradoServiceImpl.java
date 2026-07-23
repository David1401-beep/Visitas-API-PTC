package VisitasITR.API_PTC.Grado.Services.impl;

import VisitasITR.API_PTC.Especialidad.Reposity.EspecialidadRepository;
import VisitasITR.API_PTC.Grado.DTO.GradoDTO;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Reposity.GradoRepository;
import VisitasITR.API_PTC.Grado.Services.GradoService;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
import VisitasITR.API_PTC.Seccion_Tecnica.Reposity.SeccionTecnicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradoServiceImpl implements GradoService {

    private final GradoRepository gradoRepository;
    private final NivelRepository nivelRepository;
    private final SeccionTecnicaRepository seccionTecnicaRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GradoEntity> listarTodos() {
        return gradoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GradoEntity buscarPorId(Long id) {
        return gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public GradoEntity guardar(GradoDTO dto) {
        var nivel = nivelRepository.findById(dto.getIdNivel())
                .orElseThrow(() -> new RuntimeException("Nivel asociado no encontrado"));

        var tecnica = dto.getIdTecnica() != null ?
                seccionTecnicaRepository.findById(dto.getIdTecnica()).orElse(null) : null;

        var especialidad = dto.getIdEspecialidad() != null ?
                especialidadRepository.findById(dto.getIdEspecialidad()).orElse(null) : null;

        GradoEntity grado = GradoEntity.builder()
                .grado(dto.getGrado())
                .nivel(nivel)
                .seccionTecnica(tecnica)
                .especialidad(especialidad)
                .build();

        return gradoRepository.save(grado);
    }

    @Override
    @Transactional
    public GradoEntity actualizar(Long id, GradoDTO dto) {
        GradoEntity grado = buscarPorId(id);

        var nivel = nivelRepository.findById(dto.getIdNivel())
                .orElseThrow(() -> new RuntimeException("Nivel asociado no encontrado"));

        var tecnica = dto.getIdTecnica() != null ?
                seccionTecnicaRepository.findById(dto.getIdTecnica()).orElse(null) : null;

        var especialidad = dto.getIdEspecialidad() != null ?
                especialidadRepository.findById(dto.getIdEspecialidad()).orElse(null) : null;

        grado.setGrado(dto.getGrado());
        grado.setNivel(nivel);
        grado.setSeccionTecnica(tecnica);
        grado.setEspecialidad(especialidad);

        return gradoRepository.save(grado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        GradoEntity grado = buscarPorId(id);
        gradoRepository.delete(grado);
    }

    @Override
    public GradoDTO actualizarGrado(Long id, GradoDTO dto) {
        GradoEntity entidadExistente = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));

        if (dto.getGrado() != null && !dto.getGrado().isBlank()) {
            entidadExistente.setGrado(dto.getGrado());
        }

        GradoEntity actualizado = gradoRepository.save(entidadExistente);

        GradoDTO respuestaDTO = new GradoDTO();
        respuestaDTO.setIdGrado(actualizado.getIdGrado());
        respuestaDTO.setGrado(actualizado.getGrado());
        return respuestaDTO;
    }

    @Override
    public boolean eliminar2(Long id) {
        return false;
    }
}