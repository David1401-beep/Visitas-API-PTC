package VisitasITR.API_PTC.Seccion_Tecnica.Services;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
import VisitasITR.API_PTC.Seccion_Tecnica.Reposity.SeccionTecnicaRepository;
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
public class SeccionTecnicaService {

    private final SeccionTecnicaRepository repository;

    public List<SeccionTecnicaDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SeccionTecnicaDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección Técnica no encontrada: " + id)));
    }

    @Transactional
    public SeccionTecnicaDTO crear(SeccionTecnicaDTO dto) {
        if (repository.existsByTecnica(dto.getTecnica())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La sección técnica ya existe.");
        }
        SeccionTecnicaEntity entity = SeccionTecnicaEntity.builder().tecnica(dto.getTecnica()).build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public SeccionTecnicaDTO actualizar(Long id, SeccionTecnicaDTO dto) {
        SeccionTecnicaEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección Técnica no encontrada: " + id));
        entity.setTecnica(dto.getTecnica());
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sección Técnica no encontrada: " + id);
        }
        repository.deleteById(id);
    }

    private SeccionTecnicaDTO toDTO(SeccionTecnicaEntity entity) {
        return SeccionTecnicaDTO.builder().idTecnica(entity.getIdTecnica()).tecnica(entity.getTecnica()).build();
    }
}