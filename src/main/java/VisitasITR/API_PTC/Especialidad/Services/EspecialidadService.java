package VisitasITR.API_PTC.Especialidad.Services;

import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;
import VisitasITR.API_PTC.Especialidad.Reposity.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;
    @Transactional(readOnly = true)
    public List<EspecialidadEntity> listarTodos() {
        return especialidadRepository.findAll();
    }
    @Transactional(readOnly = true)
    public EspecialidadEntity buscarPorId(Long id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));
    }
    @Transactional
    public EspecialidadEntity guardar(EspecialidadDTO dto) {
        EspecialidadEntity especialidad = EspecialidadEntity.builder()
                .especialidad(dto.getEspecialidad())
                .build();
        return especialidadRepository.save(especialidad);
    }
    @Transactional
    public EspecialidadEntity actualizar(Long id, EspecialidadDTO dto) {
        EspecialidadEntity especialidad = buscarPorId(id);
        especialidad.setEspecialidad(dto.getEspecialidad());
        return especialidadRepository.save(especialidad);
    }
    @Transactional
    public void eliminar(Long id) {
        EspecialidadEntity especialidad = buscarPorId(id);
        especialidadRepository.delete(especialidad);
    }
    public EspecialidadDTO actualizarEspecialidad(Long id, EspecialidadDTO dto) {
        EspecialidadEntity entidadExistente = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con ID: " + id));

        if (dto.getEspecialidad() != null && !dto.getEspecialidad().isBlank()) {
            entidadExistente.setEspecialidad(dto.getEspecialidad());
        }

        EspecialidadEntity actualizado = especialidadRepository.save(entidadExistente);

        EspecialidadDTO respuestaDTO = new EspecialidadDTO();
        respuestaDTO.setIdEspecialidad(actualizado.getIdEspecialidad());
        respuestaDTO.setEspecialidad(actualizado.getEspecialidad());
        return respuestaDTO;
    }
    @Transactional
    public boolean eliminar2(Long id) {
        if (especialidadRepository.existsById(id)) {
            especialidadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
