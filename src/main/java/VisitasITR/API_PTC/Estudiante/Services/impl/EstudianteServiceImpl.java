package VisitasITR.API_PTC.Estudiante.Services.impl;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;
import VisitasITR.API_PTC.Detalle_Grado.Reposity.DetalleGradoRepository;
import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.Estudiante.Services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final DetalleGradoRepository detalleGradoRepository;
    private final AcademicaRepository academicaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteEntity> listarTodos() {
        return estudianteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteEntity buscarPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public EstudianteEntity guardar(EstudianteDTO dto) {
        DetalleGradoEntity detalleGrado = detalleGradoRepository.findById(dto.getIdDetalleGrado())
                .orElseThrow(() -> new RuntimeException("Detalle de grado asociado no encontrado"));

        AcademicaEntity academica = academicaRepository.findById(dto.getIdAcademica())
                .orElseThrow(() -> new RuntimeException("Sección académica asociada no encontrada"));

        EstudianteEntity estudiante = EstudianteEntity.builder()
//                .nie(dto.getNie())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .grado(dto.getGrado())
                .seccion(dto.getSeccion())
                .especialidad(dto.getEspecialidad())
                .codigo(dto.getCodigo())
                .academica(academica)
                .detalleGrado(detalleGrado)
                .build();
        return estudianteRepository.save(estudiante);
    }

    @Override
    @Transactional
    public EstudianteEntity actualizar(Long id, EstudianteDTO dto) {
        EstudianteEntity estudiante = buscarPorId(id);

        DetalleGradoEntity detalleGrado = detalleGradoRepository.findById(dto.getIdDetalleGrado())
                .orElseThrow(() -> new RuntimeException("Detalle de grado asociado no encontrado"));

        AcademicaEntity academica = academicaRepository.findById(dto.getIdAcademica())
                .orElseThrow(() -> new RuntimeException("Sección académica asociada no encontrada"));

//        estudiante.setNie(dto.getNie());
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setGrado(dto.getGrado());
        estudiante.setSeccion(dto.getSeccion());
        estudiante.setEspecialidad(dto.getEspecialidad());
        estudiante.setCodigo(dto.getCodigo());
        estudiante.setAcademica(academica);
        estudiante.setDetalleGrado(detalleGrado);

        return estudianteRepository.save(estudiante);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        EstudianteEntity estudiante = buscarPorId(id);
        estudianteRepository.delete(estudiante);
    }

    @Override
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteDTO dto) {
        EstudianteEntity entidadExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            entidadExistente.setNombre(dto.getNombre());
        }
        if (dto.getApellido() != null && !dto.getApellido().isBlank()) {
            entidadExistente.setApellido(dto.getApellido());
        }
//        if (dto.getNie() != null && !dto.getNie().isBlank()) {
//            entidadExistente.setNie(dto.getNie());
//        }
        if (dto.getGrado() != null && !dto.getGrado().isBlank()) {
            entidadExistente.setGrado(dto.getGrado());
        }
        if (dto.getSeccion() != null && !dto.getSeccion().isBlank()) {
            entidadExistente.setSeccion(dto.getSeccion());
        }
        if (dto.getEspecialidad() != null && !dto.getEspecialidad().isBlank()) {
            entidadExistente.setEspecialidad(dto.getEspecialidad());
        }
        if (dto.getCodigo() != null && !dto.getCodigo().isBlank()) {
            entidadExistente.setCodigo(dto.getCodigo());
        }
        if (dto.getIdAcademica() != null) {
            AcademicaEntity academica = academicaRepository.findById(dto.getIdAcademica())
                    .orElseThrow(() -> new RuntimeException("Sección académica no encontrada con ID: " + dto.getIdAcademica()));
            entidadExistente.setAcademica(academica);
        }
        if (dto.getIdDetalleGrado() != null) {
            DetalleGradoEntity detalleGrado = detalleGradoRepository.findById(dto.getIdDetalleGrado())
                    .orElseThrow(() -> new RuntimeException("Detalle de grado no encontrado con ID: " + dto.getIdDetalleGrado()));
            entidadExistente.setDetalleGrado(detalleGrado);
        }

        EstudianteEntity actualizado = estudianteRepository.save(entidadExistente);

        EstudianteDTO respuestaDTO = new EstudianteDTO();
        respuestaDTO.setIdEstudiante(actualizado.getIdEstudiante());
        respuestaDTO.setNombre(actualizado.getNombre());
        respuestaDTO.setApellido(actualizado.getApellido());
//        respuestaDTO.setNie(actualizado.getNie());
        respuestaDTO.setGrado(actualizado.getGrado());
        respuestaDTO.setSeccion(actualizado.getSeccion());
        respuestaDTO.setEspecialidad(actualizado.getEspecialidad());
        respuestaDTO.setCodigo(actualizado.getCodigo());
        if (actualizado.getAcademica() != null) {
            respuestaDTO.setIdAcademica(actualizado.getAcademica().getIdAcademica());
        }
        if (actualizado.getDetalleGrado() != null) {
            respuestaDTO.setIdDetalleGrado(actualizado.getDetalleGrado().getIdDetalleGrado());
        }
        return respuestaDTO;
    }

    @Override
    @Transactional
    public boolean eliminar2(Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}