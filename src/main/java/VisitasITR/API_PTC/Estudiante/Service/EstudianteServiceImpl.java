package VisitasITR.API_PTC.Estudiante.Service;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService{
    @Autowired
    private EstudianteRepository repository;
    @Override
    public List<EstudianteDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }
    @Override
    public EstudianteDTO obtenerPorId(Integer id){
        EstudianteEntity entity = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("estudiante no encontardo"));
        return convertirDTO(entity);
    }
    @Override
    public EstudianteDTO guardar(EstudianteDTO dto){
        EstudianteEntity entity = convertirEntity(dto);
        return convertirDTO(repository.save(entity));
    }
    @Override
    public EstudianteDTO actualizar(Integer id,EstudianteDTO dto){
        EstudianteEntity entity = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Estudiante no encontrado"));
        entity.setEstNombre(dto.getEstNombre());
        entity.setEstApellido(dto.getEstApellido());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        entity.setEstEspecialidad(dto.getEstEspecialidad());
        entity.setEstCodigo(dto.getEstCodigo());
        entity.setIdPadre(dto.getIdPadre());
        entity.setIdDetallesGrado(dto.getIdDetallesGrado());

        return convertirDTO(repository.save(entity));
    }
    @Override
    public void eliminar(Integer id){
        repository.deleteById(id);
    }
    private EstudianteDTO convertirDTO(EstudianteEntity entity){

        EstudianteDTO dto = new EstudianteDTO();

        dto.setIdEstudiante(entity.getIdEstudiante());
        dto.setEstNombre(entity.getEstNombre());
        dto.setEstApellido(entity.getEstApellido());
        dto.setEstGrado(entity.getEstGrado());
        dto.setEstSeccion(entity.getEstSeccion());
        dto.setEstEspecialidad(entity.getEstEspecialidad());
        dto.setEstCodigo(entity.getEstCodigo());
        dto.setIdPadre(entity.getIdPadre());
        dto.setIdDetallesGrado(entity.getIdDetallesGrado());

        return dto;
    }
    private EstudianteEntity convertirEntity(EstudianteDTO dto) {

        EstudianteEntity entity = new EstudianteEntity();

        entity.setIdEstudiante(dto.getIdEstudiante());
        entity.setEstNombre(dto.getEstNombre());
        entity.setEstApellido(dto.getEstApellido());
        entity.setEstGrado(dto.getEstGrado());
        entity.setEstSeccion(dto.getEstSeccion());
        entity.setEstEspecialidad(dto.getEstEspecialidad());
        entity.setEstCodigo(dto.getEstCodigo());
        entity.setIdPadre(dto.getIdPadre());
        entity.setIdDetallesGrado(dto.getIdDetallesGrado());

        return entity;

    }
}

