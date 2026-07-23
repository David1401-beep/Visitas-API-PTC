package VisitasITR.API_PTC.DOCENTE_GRADO.Services.impl;

import VisitasITR.API_PTC.DOCENTE_GRADO.DTO.DocenteGradoDTO;
import VisitasITR.API_PTC.DOCENTE_GRADO.Entity.DocenteGradoEntity;
import VisitasITR.API_PTC.DOCENTE_GRADO.Reposity.DocenteGradoRepository;
import VisitasITR.API_PTC.DOCENTE_GRADO.Services.DocenteGradoService;
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
public class DocenteGradoServiceImpl implements DocenteGradoService {

    private final DocenteGradoRepository docenteGradoRepository;
    private final DocenteRepository docenteRepository;
    private final GradoRepository gradoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DocenteGradoEntity> listarTodos() {
        return docenteGradoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DocenteGradoEntity buscarPorId(Long id) {
        return docenteGradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación docente-grado no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public DocenteGradoEntity guardar(DocenteGradoDTO dto) {
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

    @Override
    @Transactional
    public DocenteGradoEntity actualizar(Long id, DocenteGradoDTO dto) {
        DocenteGradoEntity relacion = buscarPorId(id);

        DocenteEntity docente = docenteRepository.findById(dto.getIdDocente())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        relacion.setDocente(docente);
        relacion.setGrado(grado);
        relacion.setAnioEscolar(dto.getAnioEscolar());

        return docenteGradoRepository.save(relacion);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        DocenteGradoEntity relacion = buscarPorId(id);
        docenteGradoRepository.delete(relacion);
    }

    @Override
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
            entidadExistente.setAnioEscolar(dto.getAnioEscolar());
        }

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

    @Override
    @Transactional
    public boolean eliminar2(Long id) {
        if (docenteGradoRepository.existsById(id)) {
            docenteGradoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
