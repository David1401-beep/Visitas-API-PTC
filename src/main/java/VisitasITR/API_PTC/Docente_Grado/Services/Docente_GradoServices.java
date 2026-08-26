package VisitasITR.API_PTC.Docente_Grado.Services;

import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Docente_Grado.DTO.Docente_GradoDTO;
import VisitasITR.API_PTC.Docente_Grado.Entity.Docente_GradoEntity;
import VisitasITR.API_PTC.Docente_Grado.Repository.Docente_GradoRepository;
import VisitasITR.API_PTC.Grado.Repository.GradoRepository;
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
public class Docente_GradoServices {

    private final Docente_GradoRepository repository;
    private final DocenteRepository docenteRepository;
    private final GradoRepository gradoRepository;

    public List<Docente_GradoDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Docente_GradoDTO obtenerPorId(Long id) {
        return toDTO(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado: " + id)));
    }

    @Transactional
    public Docente_GradoDTO crear(Docente_GradoDTO dto) {
        if (repository.existsByDocente_IdDocenteAndGrado_IdGradoAndAnioEscolar(dto.getIdDocente(), dto.getIdGrado(), dto.getAnioEscolar())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta asignación docente-grado ya existe para este año escolar.");
        }

        Docente_GradoEntity entity = Docente_GradoEntity.builder()
                .docente(docenteRepository.findById(dto.getIdDocente())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Docente no encontrado")))
                .grado(gradoRepository.findById(dto.getIdGrado())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grado no encontrado")))
                .anioEscolar(dto.getAnioEscolar())
                .build();
        return toDTO(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private Docente_GradoDTO toDTO(Docente_GradoEntity entity) {
        return Docente_GradoDTO.builder()
                .idDocenteGrado(entity.getIdDocenteGrado())
                .idDocente(entity.getDocente().getIdDocente())
                .nombreDocente(entity.getDocente().getDocNombre() + " " + entity.getDocente().getDocApellido())
                .idGrado(entity.getGrado().getIdGrado())
                .nombreGrado(entity.getGrado().getGrado())
                .anioEscolar(entity.getAnioEscolar())
                .build();
    }
}