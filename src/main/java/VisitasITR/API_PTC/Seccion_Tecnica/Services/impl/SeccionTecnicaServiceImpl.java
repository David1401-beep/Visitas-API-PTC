package VisitasITR.API_PTC.Seccion_Tecnica.Services.impl;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
import VisitasITR.API_PTC.Seccion_Tecnica.Reposity.SeccionTecnicaRepository;
import VisitasITR.API_PTC.Seccion_Tecnica.Services.SeccionTecnicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeccionTecnicaServiceImpl implements SeccionTecnicaService {

    private final SeccionTecnicaRepository seccionTecnicaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SeccionTecnicaEntity> listarTodos() {
        return seccionTecnicaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SeccionTecnicaEntity buscarPorId(Long id) {
        return seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección técnica no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public SeccionTecnicaEntity guardar(SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity tecnica = SeccionTecnicaEntity.builder()
                .tecnica(dto.getTecnica())
                .build();
        return seccionTecnicaRepository.save(tecnica);
    }

    @Override
    @Transactional
    public SeccionTecnicaEntity actualizar(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity tecnica = buscarPorId(id);
        tecnica.setTecnica(dto.getTecnica());
        return seccionTecnicaRepository.save(tecnica);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        SeccionTecnicaEntity tecnica = buscarPorId(id);
        seccionTecnicaRepository.delete(tecnica);
    }

    @Override
    public SeccionTecnicaDTO actualizarSeccionTecnica(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity entidadExistente = seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SeccionTecnica no encontrada con ID: " + id));

        if (dto.getTecnica() != null && !dto.getTecnica().isBlank()) {
            entidadExistente.setTecnica(dto.getTecnica());
        }

        SeccionTecnicaEntity actualizado = seccionTecnicaRepository.save(entidadExistente);

        SeccionTecnicaDTO respuestaDTO = new SeccionTecnicaDTO();
        respuestaDTO.setIdTecnica(actualizado.getIdTecnica());
        respuestaDTO.setTecnica(actualizado.getTecnica());
        return respuestaDTO;
    }

    @Override
    @Transactional
    public boolean eliminar2(Long id) {
        if (seccionTecnicaRepository.existsById(id)) {
            seccionTecnicaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}