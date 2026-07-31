package VisitasITR.API_PTC.Docente_Grado.Services;

import VisitasITR.API_PTC.Docente_Grado.DTO.DocenteGradoDTO;
import VisitasITR.API_PTC.Docente_Grado.Entity.DocenteGradoEntity;
import VisitasITR.API_PTC.Docente_Grado.Reposity.DocenteGradoRepository;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Reposity.GradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocenteGradoService {

    private final DocenteGradoRepository docenteGradoRepository;
    private final DocenteRepository docenteRepository;
    private final GradoRepository gradoRepository;
    @Transactional(readOnly = true)
    public List<DocenteGradoEntity> listarTodos() {
        return docenteGradoRepository.findAll();
    }
    @Transactional(readOnly = true)
    public DocenteGradoEntity buscarPorId(Long id) {
        return docenteGradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación docente-grado no encontrada con ID: " + id));
    }
    @Transactional
    public DocenteGradoEntity guardar(DocenteGradoDTO dto) {
        validarAnio(dto.getAnioEscolar());
        validarRelacionDuplicada(
                dto.getIdDocente(),
                dto.getIdGrado(),
                dto.getAnioEscolar(),
                null
        );
        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        DocenteGradoEntity relacion = DocenteGradoEntity.builder()
                .docente(docente)
                .grado(grado)
                .anioEscolar(dto.getAnioEscolar())
                .build();

        return docenteGradoRepository.save(relacion);
    }
    @Transactional
    public DocenteGradoEntity actualizar(Long id, DocenteGradoDTO dto) {
        DocenteGradoEntity relacion = buscarPorId(id);
        validarAnio(dto.getAnioEscolar());
        validarRelacionDuplicada(
                dto.getIdDocente(),
                dto.getIdGrado(),
                dto.getAnioEscolar(),
                id
        );

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        relacion.setDocente(docente);
        relacion.setGrado(grado);
        relacion.setAnioEscolar(dto.getAnioEscolar());

        return docenteGradoRepository.save(relacion);
    }
    @Transactional
    public void eliminar(Long id) {
        DocenteGradoEntity relacion = buscarPorId(id);
        docenteGradoRepository.delete(relacion);
    }
    @Transactional
    public DocenteGradoDTO actualizarDocenteGrado(Long id, DocenteGradoDTO dto) {
        DocenteGradoEntity entidadExistente = docenteGradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DocenteGrado no encontrado con ID: " + id));

        if (dto.getIdDocente() != null) {
            DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                    .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + dto.getIdDocente()));
            entidadExistente.setDocente(docente);
        }
        if (dto.getIdGrado() != null) {
            GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                    .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + dto.getIdGrado()));
            entidadExistente.setGrado(grado);
        }
        if (dto.getAnioEscolar() != null) {
            validarAnio(dto.getAnioEscolar());
            entidadExistente.setAnioEscolar(dto.getAnioEscolar());
        }

        validarRelacionDuplicada(
                entidadExistente.getDocente().getIdDocente(),
                entidadExistente.getGrado().getIdGrado(),
                entidadExistente.getAnioEscolar(),
                id
        );

        DocenteGradoEntity actualizado = docenteGradoRepository.save(entidadExistente);

        DocenteGradoDTO respuestaDTO = new DocenteGradoDTO();
        respuestaDTO.setIdDocenteGrado(actualizado.getIdDocenteGrado());
        if (actualizado.getDocente() != null) {
            respuestaDTO.setIdDocente(actualizado.getDocente().getIdDocente());
        }
        if (actualizado.getGrado() != null) {
            respuestaDTO.setIdGrado(actualizado.getGrado().getIdGrado());
        }
        respuestaDTO.setAnioEscolar(actualizado.getAnioEscolar());
        return respuestaDTO;
    }

    private void validarAnio(Integer anioEscolar) {
        if (anioEscolar == null || anioEscolar < 2000 || anioEscolar > 2100) {
            throw new RuntimeException("El año escolar debe estar entre 2000 y 2100");
        }
    }

    private void validarRelacionDuplicada(
            Long idDocente,
            Long idGrado,
            Integer anioEscolar,
            Long idRelacionActual
    ) {
        boolean existe = docenteGradoRepository
                .existsByDocente_IdDocenteAndGrado_IdGradoAndAnioEscolar(
                        idDocente,
                        idGrado,
                        anioEscolar
                );
        if (!existe) {
            return;
        }
        if (idRelacionActual == null) {
            throw new RuntimeException(
                    "La asignación docente-grado ya existe para ese año escolar"
            );
        }
        DocenteGradoEntity actual = buscarPorId(idRelacionActual);
        boolean esLaMismaRelacion =
                actual.getDocente().getIdDocente().equals(idDocente)
                        && actual.getGrado().getIdGrado().equals(idGrado)
                        && actual.getAnioEscolar().equals(anioEscolar);
        if (!esLaMismaRelacion) {
            throw new RuntimeException(
                    "La asignación docente-grado ya existe para ese año escolar"
            );
        }
    }
    @Transactional
    public boolean eliminar2(Long id) {
        if (docenteGradoRepository.existsById(id)) {
            docenteGradoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
