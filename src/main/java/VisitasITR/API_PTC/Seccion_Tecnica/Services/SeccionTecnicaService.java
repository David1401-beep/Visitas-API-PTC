package VisitasITR.API_PTC.Seccion_Tecnica.Services;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
import VisitasITR.API_PTC.Seccion_Tecnica.Reposity.SeccionTecnicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeccionTecnicaService {

    private final SeccionTecnicaRepository seccionTecnicaRepository;
    @Transactional(readOnly = true)
    public List<SeccionTecnicaEntity> listarTodos() {
        return seccionTecnicaRepository.findAll();
    }
    @Transactional(readOnly = true)
    public SeccionTecnicaEntity buscarPorId(Long id) {
        return seccionTecnicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección técnica no encontrada con ID: " + id));
    }
    @Transactional
    public SeccionTecnicaEntity guardar(SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity tecnica = SeccionTecnicaEntity.builder()
                .tecnica(dto.getTecnica())
                .build();
        return seccionTecnicaRepository.save(tecnica);
    }
    @Transactional
    public SeccionTecnicaEntity actualizar(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity tecnica = buscarPorId(id);
        tecnica.setTecnica(dto.getTecnica());
        return seccionTecnicaRepository.save(tecnica);
    }
    @Transactional
    public void eliminar(Long id) {
        SeccionTecnicaEntity tecnica = buscarPorId(id);
        seccionTecnicaRepository.delete(tecnica);
    }
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
    @Transactional
    public boolean eliminar2(Long id) {
        if (seccionTecnicaRepository.existsById(id)) {
            seccionTecnicaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
