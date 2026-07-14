package VisitasITR.API_PTC.Academica.Service;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcademicaService {

    private final AcademicaRepository repository;

    public AcademicaService(AcademicaRepository repository) {
        this.repository = repository;

    }

    public AcademicaDTO nuevaAcademica(AcademicaDTO dto) {
        AcademicaEntity entity = new AcademicaEntity();
        entity.setAcademica(dto.getAcademica());

        AcademicaEntity entidadGuardada = repository.save(entity);

        dto.setIdAcademica(entidadGuardada.getIdAcademica());
        return dto;
    }

    public List<AcademicaDTO> obtenerDatosAcademica() {
        return repository.findAll().stream().map(entity -> {
            AcademicaDTO dto = new AcademicaDTO();
            dto.setIdAcademica(entity.getIdAcademica());
            dto.setAcademica(entity.getAcademica());
            return dto;
        }).collect(Collectors.toList());
    }

    // Obtener por ID
    public AcademicaDTO obtenerPorId(Long id) {
        AcademicaEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el registro con ID: " + id));

        AcademicaDTO dto = new AcademicaDTO();
        dto.setIdAcademica(entity.getIdAcademica());
        dto.setAcademica(entity.getAcademica());
        return dto;
    }

    public boolean eliminarAcademica(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
