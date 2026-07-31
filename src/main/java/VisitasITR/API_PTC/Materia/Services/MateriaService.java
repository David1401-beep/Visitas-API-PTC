package VisitasITR.API_PTC.Materia.Services;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    @Transactional(readOnly = true)
    public List<MateriaEntity> listarTodos() {
        return materiaRepository.findAll();
    }
    @Transactional(readOnly = true)
    public MateriaEntity buscarPorId(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));
    }
    @Transactional
    public MateriaEntity guardar(MateriaDTO dto) {
        MateriaEntity materia = MateriaEntity.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .build();
        return materiaRepository.save(materia);
    }
    @Transactional
    public MateriaEntity actualizar(Long id, MateriaDTO dto) {
        MateriaEntity materia = buscarPorId(id);
        materia.setNombre(dto.getNombre());
        materia.setTipo(dto.getTipo());
        return materiaRepository.save(materia);
    }
    @Transactional
    public void eliminar(Long id) {
        MateriaEntity materia = buscarPorId(id);
        materiaRepository.delete(materia);
    }
    public MateriaDTO actualizarMateria(Long id, MateriaDTO dto) {
        MateriaEntity entidadExistente = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entidadExistente.setNombre(dto.getNombre());
        }

        MateriaEntity actualizado = materiaRepository.save(entidadExistente);

        MateriaDTO respuestaDTO = new MateriaDTO();
        respuestaDTO.setIdMateria(actualizado.getIdMateria());
        respuestaDTO.setNombre(actualizado.getNombre());
        return respuestaDTO;
    }
    @Transactional
    public boolean eliminar2(Long id) {
        if (materiaRepository.existsById(id)) {
            materiaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
