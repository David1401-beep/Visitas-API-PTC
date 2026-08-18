package VisitasITR.API_PTC.Materia_Docente.Services;

import VisitasITR.API_PTC.Empleado.Entity.EmpleadoEntity;
import VisitasITR.API_PTC.Empleado.Repository.EmpleadoRepository;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Repository.MateriaRepository;
import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import VisitasITR.API_PTC.Materia_Docente.Reposity.MateriaDocenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaDocenteService {

    @Autowired
    private MateriaDocenteRepository materiaDocenteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<MateriaDocenteDTO> listarTodos() {
        return materiaDocenteRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MateriaDocenteDTO buscarPorId(Long id) {
        MateriaDocenteEntity entity = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));
        return convertirADTO(entity);
    }

    public MateriaDocenteDTO guardar(MateriaDocenteDTO dto) {
        if (materiaDocenteRepository.existsByEmpleado_IdEmpleado(dto.getIdEmpleado())) {
            throw new RuntimeException("El empleado con ID " + dto.getIdEmpleado() + " ya tiene una materia asignada.");
        }

        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new RuntimeException("La materia con ID " + dto.getIdMateria() + " no existe."));

        EmpleadoEntity empleado = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("El empleado con ID " + dto.getIdEmpleado() + " no existe."));

        MateriaDocenteEntity entity = MateriaDocenteEntity.builder()
                .materia(materia)
                .empleado(empleado)
                .build();

        return convertirADTO(materiaDocenteRepository.save(entity));
    }

    @Transactional
    public MateriaDocenteDTO actualizar(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity entity = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));

        // Si cambió el empleado, verificar que el nuevo no esté repetido en otro registro
        if (!entity.getEmpleado().getIdEmpleado().equals(dto.getIdEmpleado())) {
            if (materiaDocenteRepository.existsByEmpleado_IdEmpleado(dto.getIdEmpleado())) {
                throw new RuntimeException("El empleado con ID " + dto.getIdEmpleado() + " ya tiene una materia asignada.");
            }
        }

        MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + dto.getIdMateria()));

        EmpleadoEntity empleado = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + dto.getIdEmpleado()));

        entity.setMateria(materia);
        entity.setEmpleado(empleado);

        return convertirADTO(materiaDocenteRepository.save(entity));
    }

    @Transactional
    public MateriaDocenteDTO actualizarParcial(Long id, MateriaDocenteDTO dto) {
        MateriaDocenteEntity entity = materiaDocenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));

        if (dto.getIdMateria() != null) {
            MateriaEntity materia = materiaRepository.findById(dto.getIdMateria())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + dto.getIdMateria()));
            entity.setMateria(materia);
        }

        if (dto.getIdEmpleado() != null) {
            if (!entity.getEmpleado().getIdEmpleado().equals(dto.getIdEmpleado())) {
                if (materiaDocenteRepository.existsByEmpleado_IdEmpleado(dto.getIdEmpleado())) {
                    throw new RuntimeException("El empleado con ID " + dto.getIdEmpleado() + " ya tiene una materia asignada.");
                }
            }
            EmpleadoEntity empleado = empleadoRepository.findById(dto.getIdEmpleado())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + dto.getIdEmpleado()));
            entity.setEmpleado(empleado);
        }

        return convertirADTO(materiaDocenteRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!materiaDocenteRepository.existsById(id)) {
            throw new RuntimeException("Registro no encontrado con ID: " + id);
        }
        materiaDocenteRepository.deleteById(id);
    }

    private MateriaDocenteDTO convertirADTO(MateriaDocenteEntity entity) {
        return MateriaDocenteDTO.builder()
                .idMateriaDocente(entity.getIdMateriaDocente())
                .idMateria(entity.getMateria().getIdMateria())
                .nombreMateria(entity.getMateria().getNombre())
                .idEmpleado(entity.getEmpleado().getIdEmpleado())
                .nombreEmpleado(entity.getEmpleado().getEmpNombre() + " " + entity.getEmpleado().getEmpApellido())
                .build();
    }
}