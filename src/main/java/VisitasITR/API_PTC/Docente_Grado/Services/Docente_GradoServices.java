package VisitasITR.API_PTC.Docente_Grado.Services;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Docente_Grado.DTO.Docente_GradoDTO;
import VisitasITR.API_PTC.Docente_Grado.Entity.Docente_GradoEntity;
import VisitasITR.API_PTC.Docente_Grado.Repository.Docente_GradoRepository;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
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

    private final Docente_GradoRepository docenteGradoRepository;
    private final DocenteRepository docenteRepository;
    private final GradoRepository gradoRepository;

    public List<Docente_GradoDTO> obtenerTodos() {
        return docenteGradoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Docente_GradoDTO obtenerPorId(Long id) {
        Docente_GradoEntity entity = docenteGradoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Asignación Docente-Grado no encontrada con ID: " + id));
        return convertirADTO(entity);
    }

    @Transactional
    public Docente_GradoDTO guardar(Docente_GradoDTO dto) {
        if (docenteGradoRepository.existsByDocenteIdDocenteAndGradoIdGradoAndAnioEscolar(
                dto.getIdDocente(), dto.getIdGrado(), dto.getAnioEscolar())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "El docente ya tiene asignado este grado para el año escolar indicado.");
        }

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + dto.getIdDocente()));

        GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grado no encontrado con ID: " + dto.getIdGrado()));

        Docente_GradoEntity entity = Docente_GradoEntity.builder()
                .docente(docente)
                .grado(grado)
                .anioEscolar(dto.getAnioEscolar())
                .build();

        return convertirADTO(docenteGradoRepository.save(entity));
    }

    @Transactional
    public Docente_GradoDTO actualizar(Long id, Docente_GradoDTO dto) {
        Docente_GradoEntity entity = docenteGradoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Asignación Docente-Grado no encontrada con ID: " + id));

        if (docenteGradoRepository.existsByDocenteIdDocenteAndGradoIdGradoAndAnioEscolarAndIdDocenteGradoNot(
                dto.getIdDocente(), dto.getIdGrado(), dto.getAnioEscolar(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La asignación del docente para ese grado y año escolar ya existe.");
        }

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + dto.getIdDocente()));

        GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Grado no encontrado con ID: " + dto.getIdGrado()));

        entity.setDocente(docente);
        entity.setGrado(grado);
        entity.setAnioEscolar(dto.getAnioEscolar());

        return convertirADTO(docenteGradoRepository.save(entity));
    }

    @Transactional
    public Docente_GradoDTO actualizarParcial(Long id, Docente_GradoDTO dto) {
        Docente_GradoEntity entity = docenteGradoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Asignación Docente-Grado no encontrada con ID: " + id));

        Long idDocenteNuevo = dto.getIdDocente() != null ? dto.getIdDocente() : entity.getDocente().getIdDocente();
        Long idGradoNuevo = dto.getIdGrado() != null ? dto.getIdGrado() : entity.getGrado().getIdGrado();
        Integer anioNuevo = dto.getAnioEscolar() != null ? dto.getAnioEscolar() : entity.getAnioEscolar();

        if (docenteGradoRepository.existsByDocenteIdDocenteAndGradoIdGradoAndAnioEscolarAndIdDocenteGradoNot(
                idDocenteNuevo, idGradoNuevo, anioNuevo, id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe un registro con la combinación de docente, grado y año escolar.");
        }

        if (dto.getIdDocente() != null) {
            DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Docente no encontrado con ID: " + dto.getIdDocente()));
            entity.setDocente(docente);
        }

        if (dto.getIdGrado() != null) {
            GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Grado no encontrado con ID: " + dto.getIdGrado()));
            entity.setGrado(grado);
        }

        if (dto.getAnioEscolar() != null) {
            entity.setAnioEscolar(dto.getAnioEscolar());
        }

        return convertirADTO(docenteGradoRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!docenteGradoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Asignación Docente-Grado no encontrada con ID: " + id);
        }
        docenteGradoRepository.deleteById(id);
    }

    private Docente_GradoDTO convertirADTO(Docente_GradoEntity entity) {
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