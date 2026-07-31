package VisitasITR.API_PTC.Estudiante_Encargado.Services;

import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Reposity.EncargadoRepository;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Reposity.EstudianteRepository;
import VisitasITR.API_PTC.Estudiante_Encargado.DTO.EstudianteEncargadoDTO;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Reposity.EstudianteEncargadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteEncargadoService {

    private final EstudianteEncargadoRepository estudianteEncargadoRepository;
    private final EstudianteRepository estudianteRepository;
    private final EncargadoRepository encargadoRepository;

    @Transactional(readOnly = true)
    public List<EstudianteEncargadoEntity> listarTodos() {
        return estudianteEncargadoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EstudianteEncargadoEntity buscarPorId(Long id) {
        return estudianteEncargadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Relación estudiante-encargado no encontrada con ID: " + id
                ));
    }

    @Transactional
    public EstudianteEncargadoEntity guardar(EstudianteEncargadoDTO dto) {
        validarParentesco(dto.getParentesco());
        validarRelacionDuplicada(dto.getIdEstudiante(), dto.getIdPadre(), null);

        EstudianteEncargadoEntity relacion = EstudianteEncargadoEntity.builder()
                .estudiante(buscarEstudiante(dto.getIdEstudiante()))
                .encargado(buscarEncargado(dto.getIdPadre()))
                .parentesco(dto.getParentesco())
                .build();

        return estudianteEncargadoRepository.save(relacion);
    }

    @Transactional
    public EstudianteEncargadoEntity actualizar(Long id, EstudianteEncargadoDTO dto) {
        EstudianteEncargadoEntity relacion = buscarPorId(id);
        validarParentesco(dto.getParentesco());
        validarRelacionDuplicada(dto.getIdEstudiante(), dto.getIdPadre(), id);

        relacion.setEstudiante(buscarEstudiante(dto.getIdEstudiante()));
        relacion.setEncargado(buscarEncargado(dto.getIdPadre()));
        relacion.setParentesco(dto.getParentesco());
        return estudianteEncargadoRepository.save(relacion);
    }

    @Transactional
    public EstudianteEncargadoDTO actualizarParcial(Long id, EstudianteEncargadoDTO dto) {
        EstudianteEncargadoEntity relacion = buscarPorId(id);

        Long idEstudiante = dto.getIdEstudiante() != null
                ? dto.getIdEstudiante()
                : relacion.getEstudiante().getIdEstudiante();
        Long idPadre = dto.getIdPadre() != null
                ? dto.getIdPadre()
                : relacion.getEncargado().getIdPadre();

        validarRelacionDuplicada(idEstudiante, idPadre, id);

        if (dto.getIdEstudiante() != null) {
            relacion.setEstudiante(buscarEstudiante(dto.getIdEstudiante()));
        }
        if (dto.getIdPadre() != null) {
            relacion.setEncargado(buscarEncargado(dto.getIdPadre()));
        }
        if (dto.getParentesco() != null && !dto.getParentesco().isBlank()) {
            validarParentesco(dto.getParentesco());
            relacion.setParentesco(dto.getParentesco());
        }

        return convertirADto(estudianteEncargadoRepository.save(relacion));
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!estudianteEncargadoRepository.existsById(id)) {
            return false;
        }
        estudianteEncargadoRepository.deleteById(id);
        return true;
    }

    private EstudianteEntity buscarEstudiante(Long idEstudiante) {
        return estudianteRepository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException(
                        "Estudiante no encontrado con ID: " + idEstudiante
                ));
    }

    private void validarParentesco(String parentesco) {
        if (parentesco == null || !List.of(
                "PADRE",
                "MADRE",
                "HERMANO MAYOR",
                "HERMANA MAYOR",
                "TIO",
                "TIA",
                "ABUELO",
                "ABUELA"
        ).contains(parentesco)) {
            throw new RuntimeException("El parentesco enviado no está permitido");
        }
    }

    private EncargadoEntity buscarEncargado(Long idPadre) {
        return encargadoRepository.findById(idPadre)
                .orElseThrow(() -> new RuntimeException(
                        "Encargado no encontrado con ID: " + idPadre
                ));
    }

    private void validarRelacionDuplicada(Long idEstudiante, Long idPadre, Long idActual) {
        if (!estudianteEncargadoRepository
                .existsByEstudiante_IdEstudianteAndEncargado_IdPadre(idEstudiante, idPadre)) {
            return;
        }

        if (idActual == null) {
            throw new RuntimeException("El estudiante ya está relacionado con este encargado");
        }

        EstudianteEncargadoEntity actual = buscarPorId(idActual);
        boolean esLaMismaRelacion =
                actual.getEstudiante().getIdEstudiante().equals(idEstudiante)
                        && actual.getEncargado().getIdPadre().equals(idPadre);
        if (!esLaMismaRelacion) {
            throw new RuntimeException("El estudiante ya está relacionado con este encargado");
        }
    }

    private EstudianteEncargadoDTO convertirADto(EstudianteEncargadoEntity relacion) {
        return EstudianteEncargadoDTO.builder()
                .idEstudianteEncargado(relacion.getIdEstudianteEncargado())
                .idEstudiante(relacion.getEstudiante().getIdEstudiante())
                .idPadre(relacion.getEncargado().getIdPadre())
                .parentesco(relacion.getParentesco())
                .build();
    }
}
