package VisitasITR.API_PTC.Cita_Reunion.Service;

import VisitasITR.API_PTC.Cita_Reunion.DTO.Cita_ReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.Cita_ReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Repository.Cita_ReunionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Cita_ReunionService {
    private final Cita_ReunionRepository repository;

    public Cita_ReunionService(Cita_ReunionRepository repository) {
        this.repository = repository;
    }

    public Cita_ReunionDTO nuevaCitaReunion(Cita_ReunionDTO dto) {
        Cita_ReunionEntity entity = new Cita_ReunionEntity();
        entity.setIdDocente(dto.getIdDocente());
        entity.setIdPadre(dto.getIdPadre());
        entity.setCitFechaReunion(dto.getCitFechaReunion());
        entity.setCitMotivo(dto.getCitMotivo());
        entity.setCitEstado(dto.getCitEstado());
        entity.setCitObservaciones(dto.getCitObservaciones());

        Cita_ReunionEntity entidadGuardada = repository.save(entity);

        dto.setIdCita(entidadGuardada.getIdCita());
        return dto;
    }

    public List<Cita_ReunionDTO> obtenerDatosCitas() {
        return repository.findAll().stream().map(entity -> {
            Cita_ReunionDTO dto = new Cita_ReunionDTO();
            dto.setIdCita(entity.getIdCita());
            dto.setIdDocente(entity.getIdDocente());
            dto.setIdPadre(entity.getIdPadre());
            dto.setCitFechaReunion(entity.getCitFechaReunion());
            dto.setCitMotivo(entity.getCitMotivo());
            dto.setCitEstado(entity.getCitEstado());
            dto.setCitObservaciones(entity.getCitObservaciones());
            return dto;
        }).collect(Collectors.toList());
    }

    public Cita_ReunionDTO obtenerPorId(Long id) {
        Cita_ReunionEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la cita con ID: " + id));

        Cita_ReunionDTO dto = new Cita_ReunionDTO();
        dto.setIdCita(entity.getIdCita());
        dto.setIdDocente(entity.getIdDocente());
        dto.setIdPadre(entity.getIdPadre());
        dto.setCitFechaReunion(entity.getCitFechaReunion());
        dto.setCitMotivo(entity.getCitMotivo());
        dto.setCitEstado(entity.getCitEstado());
        dto.setCitObservaciones(entity.getCitObservaciones());
        return dto;
    }

    public boolean eliminarCitaReunion(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
