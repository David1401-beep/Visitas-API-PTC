package VisitasITR.API_PTC.Materia.Services;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateriaService {

    private final MateriaRepository materiaRepository;

    public List<MateriaDTO> listarTodos() {
        return materiaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MateriaDTO buscarPorId(Long id) {
        MateriaEntity entity = materiaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public MateriaDTO guardar(MateriaDTO dto) {
        String nombreLimpio = dto.getNombre().trim().toUpperCase();

        if (materiaRepository.existsByNombreIgnoreCase(nombreLimpio)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La materia '" + nombreLimpio + "' ya se encuentra registrada.");
        }

        MateriaEntity entity = MateriaEntity.builder()
                .nombre(nombreLimpio)
                .tipo(dto.getTipo().trim().toUpperCase())
                .build();

        return convertirADTO(materiaRepository.save(entity));
    }

    @Transactional
    public MateriaDTO actualizar(Long id, MateriaDTO dto) {
        MateriaEntity entity = materiaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada con ID: " + id));

        String nombreLimpio = dto.getNombre().trim().toUpperCase();

        if (materiaRepository.existsByNombreIgnoreCaseAndIdMateriaNot(nombreLimpio, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La materia '" + nombreLimpio + "' ya existe en otro registro.");
        }

        entity.setNombre(nombreLimpio);
        entity.setTipo(dto.getTipo().trim().toUpperCase());

        return convertirADTO(materiaRepository.save(entity));
    }

    @Transactional
    public MateriaDTO actualizarParcial(Long id, MateriaDTO dto) {
        MateriaEntity entity = materiaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            String nombreLimpio = dto.getNombre().trim().toUpperCase();
            if (materiaRepository.existsByNombreIgnoreCaseAndIdMateriaNot(nombreLimpio, id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "La materia '" + nombreLimpio + "' ya existe en otro registro.");
            }
            entity.setNombre(nombreLimpio);
        }

        if (dto.getTipo() != null && !dto.getTipo().isBlank()) {
            entity.setTipo(dto.getTipo().trim().toUpperCase());
        }

        return convertirADTO(materiaRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Materia no encontrada para eliminar con ID: " + id);
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