package VisitasITR.API_PTC.Materia.Services;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    public List<MateriaDTO> listarTodos() {
        return materiaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MateriaDTO buscarPorId(Long id) {
        MateriaEntity entity = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));
        return convertirADTO(entity);
    }

    public MateriaDTO guardar(MateriaDTO dto) {
        MateriaEntity entity = MateriaEntity.builder()
                .nombre(dto.getNombre().toUpperCase().trim())
                .tipo(dto.getTipo().toUpperCase().trim())
                .build();
        return convertirADTO(materiaRepository.save(entity));
    }

    public MateriaDTO actualizar(Long id, MateriaDTO dto) {
        MateriaEntity entity = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));

        entity.setNombre(dto.getNombre().toUpperCase().trim());
        entity.setTipo(dto.getTipo().toUpperCase().trim());

        return convertirADTO(materiaRepository.save(entity));
    }

    public MateriaDTO actualizarParcial(Long id, MateriaDTO dto) {
        MateriaEntity entity = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entity.setNombre(dto.getNombre().toUpperCase().trim());
        }
        if (dto.getTipo() != null && !dto.getTipo().isBlank()) {
            entity.setTipo(dto.getTipo().toUpperCase().trim());
        }

        return convertirADTO(materiaRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new RuntimeException("Materia no encontrada con ID: " + id);
        }
        materiaRepository.deleteById(id);
    }

    private MateriaDTO convertirADTO(MateriaEntity entity) {
        return MateriaDTO.builder()
                .idMateria(entity.getIdMateria())
                .nombre(entity.getNombre())
                .tipo(entity.getTipo())
                .build();
    }
}