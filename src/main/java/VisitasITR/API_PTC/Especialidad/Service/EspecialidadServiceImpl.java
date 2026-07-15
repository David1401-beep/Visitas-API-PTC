package VisitasITR.API_PTC.Especialidad.Service;

import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;
import VisitasITR.API_PTC.Especialidad.Repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadServiceImpl implements EspecialidadService {
    @Autowired
    private EspecialidadRepository repository;

    @Override
    public List<EspecialidadDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EspecialidadDTO obtenerPorId(Integer id){

        EspecialidadEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        return convertirDTO(entity);
    }

    @Override
    public EspecialidadDTO guardar(EspecialidadDTO dto){
        EspecialidadEntity entity = convertirEntity(dto);
        return convertirDTO(repository.save(entity));
    }

    @Override
    public EspecialidadDTO actualizar(Integer id, EspecialidadDTO dto){
        EspecialidadEntity entity = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("especialidad no encontrada"));
        entity.setEspecialidad(dto.getEspecialidad());
        return convertirDTO(repository.save(entity));
    }
    @Override
    public void eliminar(Integer id){
        repository.deleteById(id);
    }

    private EspecialidadDTO convertirDTO(EspecialidadEntity entity){
        EspecialidadDTO dto = new EspecialidadDTO();

        dto.setIdEspecialidad(entity.getIdEspecialidad());
        dto.setEspecialidad(entity.getEspecialidad());

        return dto;
    }

    private EspecialidadEntity convertirEntity(EspecialidadDTO dto){
        EspecialidadEntity entity = new EspecialidadEntity();

        entity.setIdEspecialidad(dto.getIdEspecialidad());
        entity.setEspecialidad(dto.getEspecialidad());

        return entity;
    }


}

