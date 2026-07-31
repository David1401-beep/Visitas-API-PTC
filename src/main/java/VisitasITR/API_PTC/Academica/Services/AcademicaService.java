package VisitasITR.API_PTC.Academica.Services;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademicaService {

    private final AcademicaRepository academicaRepository;

    public List<AcademicaDTO> listarTodos() {

        return academicaRepository.findAll()
                .stream()
                .map(this::convertirEntityADto)
                .collect(Collectors.toList());

    }
    public AcademicaDTO buscarPorId(Long id) {

        AcademicaEntity entidad = academicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Sección académica no encontrada con el ID: " + id));

        return convertirEntityADto(entidad);
    }
    public AcademicaDTO guardar(AcademicaDTO dto) {

        AcademicaEntity entidad = convertirDtoAEntity(dto);
        AcademicaEntity guardado = academicaRepository.save(entidad);

        return convertirEntityADto(guardado);
    }

    public AcademicaDTO actualizar(Long id, AcademicaDTO dto) {

        AcademicaEntity entidadExistente = academicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Sección académica no encontrada con el ID: " + id));
        entidadExistente.setSeccion(dto.getSeccion());

        AcademicaEntity actualizado = academicaRepository.save(entidadExistente);
        return convertirEntityADto(actualizado);
    }

    public AcademicaDTO actualizarAcademica(Long id, AcademicaDTO dto) {

        AcademicaEntity entidadExistente = academicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección académica no encontrada con el ID: " + id));

        if (dto.getSeccion() != null && !dto.getSeccion().isBlank()) {
            entidadExistente.setSeccion(dto.getSeccion());
        }

        AcademicaEntity actualizado = academicaRepository.save(entidadExistente);
        return convertirEntityADto(actualizado);
    }

    public void eliminar(Long id) {
        if (!academicaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. ID no encontrado: " + id);
        }
        academicaRepository.deleteById(id);
    }


    private AcademicaDTO convertirEntityADto(AcademicaEntity entity) {
        AcademicaDTO dto = new AcademicaDTO();
        dto.setIdAcademica(entity.getIdAcademica());
        dto.setSeccion(entity.getSeccion());
        return dto;
    }

    private AcademicaEntity convertirDtoAEntity(AcademicaDTO dto) {
        AcademicaEntity entity = new AcademicaEntity();
        entity.setIdAcademica(dto.getIdAcademica());
        entity.setSeccion(dto.getSeccion());
        return entity;
    }
    @Transactional
    public boolean eliminar2(Long id) {
        if (academicaRepository.existsById(id)) {
            academicaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
