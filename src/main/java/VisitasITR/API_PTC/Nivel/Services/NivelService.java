package VisitasITR.API_PTC.Nivel.Services;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
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
public class NivelService {

    private final NivelRepository repository;

    public List<NivelDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public NivelDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Nivel no encontrado: " + id)));
    }

    @Transactional
    public NivelDTO crear(NivelDTO dto) {
        if (repository.existsByNivel(dto.getNivel())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nivel ya existe.");
        }
        NivelEntity entity = NivelEntity.builder().nivel(dto.getNivel()).build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public NivelDTO actualizar(Long id, NivelDTO dto) {
        NivelEntity entity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Nivel no encontrado: " + id));
        entity.setNivel(dto.getNivel());
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nivel no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private NivelDTO toDTO(NivelEntity entity) {
        return NivelDTO.builder().idNivel(entity.getIdNivel()).nivel(entity.getNivel()).build();
    }
}