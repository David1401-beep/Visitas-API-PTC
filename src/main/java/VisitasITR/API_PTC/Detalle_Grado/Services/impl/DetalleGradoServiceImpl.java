package VisitasITR.API_PTC.Detalle_Grado.Services.impl;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Repository.AcademicaRepository;
import VisitasITR.API_PTC.Detalle_Grado.DTO.DetalleGradoDTO;
import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;
import VisitasITR.API_PTC.Detalle_Grado.Reposity.DetalleGradoRepository;
import VisitasITR.API_PTC.Detalle_Grado.Services.DetalleGradoService;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Reposity.GradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleGradoServiceImpl implements DetalleGradoService {

    private final DetalleGradoRepository detalleGradoRepository;
    private final GradoRepository gradoRepository;
    private final AcademicaRepository academicaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleGradoEntity> listarTodos() {
        return detalleGradoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleGradoEntity buscarPorId(Long id) {
        return detalleGradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de grado no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public DetalleGradoEntity guardar(DetalleGradoDTO dto) {
        GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        AcademicaEntity academica = academicaRepository.findById(dto.getIdAcademica())
                .orElseThrow(() -> new RuntimeException("Sección académica no encontrada"));

        DetalleGradoEntity detalle = DetalleGradoEntity.builder()
                .grado(grado)
                .academica(academica)
                .build();

        return detalleGradoRepository.save(detalle);
    }

    @Override
    @Transactional
    public DetalleGradoEntity actualizar(Long id, DetalleGradoDTO dto) {
        DetalleGradoEntity detalle = buscarPorId(id);

        GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        AcademicaEntity academica = academicaRepository.findById(dto.getIdAcademica())
                .orElseThrow(() -> new RuntimeException("Sección académica no encontrada"));

        detalle.setGrado(grado);
        detalle.setAcademica(academica);

        return detalleGradoRepository.save(detalle);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        DetalleGradoEntity detalle = buscarPorId(id);
        detalleGradoRepository.delete(detalle);
    }

    @Override
    public DetalleGradoDTO actualizarDetalleGrado(Long id, DetalleGradoDTO dto) {
        DetalleGradoEntity entidadExistente = detalleGradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de grado no encontrado con ID: " + id));

        if (dto.getIdGrado() != null) {
            GradoEntity grado = gradoRepository.findById(dto.getIdGrado())
                    .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + dto.getIdGrado()));
            entidadExistente.setGrado(grado);
        }

        DetalleGradoEntity actualizado = detalleGradoRepository.save(entidadExistente);
        DetalleGradoDTO respuestaDTO = new DetalleGradoDTO();
        respuestaDTO.setIdDetalleGrado(actualizado.getIdDetalleGrado());
        if (actualizado.getGrado() != null) {
            respuestaDTO.setIdGrado(actualizado.getGrado().getIdGrado());
        }

        return respuestaDTO;
    }

    @Override
    @Transactional
    public boolean eliminar2(Long id) {
        if (detalleGradoRepository.existsById(id)) {
            detalleGradoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
