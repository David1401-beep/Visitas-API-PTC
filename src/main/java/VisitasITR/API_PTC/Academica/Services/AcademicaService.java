package VisitasITR.API_PTC.Academica.Services;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
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
public class AcademicaService {

    private final AcademicaRepository academicaRepository;

    public List<AcademicaDTO> listarTodos() {
        return academicaRepository.findAll()
                .stream()
                .map(this::convertirEntityADto)
                .collect(Collectors.toList());
    }

    public AcademicaDTO buscarPorId(Long id) {
        AcademicaEntity entidad = academicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección académica no encontrada con el ID: " + id));

        return convertirEntityADto(entidad);
    }

<<<<<<< HEAD
    @Transactional // Sobrescribe para operaciones de escritura
=======
    // Por defecto, los métodos de esta clase trabajan como operaciones de lectura.
// Los métodos que modifican datos llevan su propio @Transactional.
    @Transactional
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    public AcademicaDTO guardar(AcademicaDTO dto) {
        if (academicaRepository.existsBySeccionIgnoreCase(dto.getSeccion())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La sección académica '" + dto.getSeccion() + "' ya se encuentra registrada.");
        }

        AcademicaEntity entidad = convertirDtoAEntity(dto);
        entidad.setIdAcademica(null);

        AcademicaEntity guardado = academicaRepository.save(entidad);
        return convertirEntityADto(guardado);
    }

    @Transactional
    public AcademicaDTO actualizar(Long id, AcademicaDTO dto) {
        AcademicaEntity entidadExistente = academicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección académica no encontrada con el ID: " + id));

        if (academicaRepository.existsBySeccionIgnoreCaseAndIdAcademicaNot(dto.getSeccion(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La sección académica '" + dto.getSeccion() + "' ya existe en otro registro.");
        }

        entidadExistente.setSeccion(dto.getSeccion());
        AcademicaEntity actualizado = academicaRepository.save(entidadExistente);
        return convertirEntityADto(actualizado);
    }

    @Transactional
    public AcademicaDTO actualizarAcademica(Long id, AcademicaDTO dto) {
        AcademicaEntity entidadExistente = academicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sección académica no encontrada con el ID: " + id));

        if (dto.getSeccion() != null && !dto.getSeccion().isBlank()) {
            if (academicaRepository.existsBySeccionIgnoreCaseAndIdAcademicaNot(dto.getSeccion(), id)) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "La sección académica '" + dto.getSeccion() + "' ya existe en otro registro.");
            }
            entidadExistente.setSeccion(dto.getSeccion());
        }

        AcademicaEntity actualizado = academicaRepository.save(entidadExistente);
        return convertirEntityADto(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!academicaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No se encuentra la sección académica con el ID: " + id);
        }
        academicaRepository.deleteById(id);
    }

    private AcademicaDTO convertirEntityADto(AcademicaEntity entity) {
        return AcademicaDTO.builder()
                .idAcademica(entity.getIdAcademica())
                .seccion(entity.getSeccion())
                .build();
    }

    private AcademicaEntity convertirDtoAEntity(AcademicaDTO dto) {
        return AcademicaEntity.builder()
                .idAcademica(dto.getIdAcademica())
                .seccion(dto.getSeccion())
                .build();
    }
}