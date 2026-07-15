package VisitasITR.API_PTC.Docente.Service;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Repository.DocenteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {


    private final DocenteRepository repo;

    public DocenteService(DocenteRepository repo) {
        this.repo = repo;
    }


    public DocenteDTO nuevoDocente(DocenteDTO dto){
        DocenteEntity datosConvertidos = convertirAEntity(dto);

        DocenteEntity respuesta = repo.save(datosConvertidos);
        return convertirADTO(respuesta);
    }

    private DocenteDTO convertirADTO(DocenteEntity respuesta){
        DocenteDTO dto = new DocenteDTO();
        dto.setId(respuesta.getId());
        dto.setDocNombre(respuesta.getDocNombre());
        dto.setDocApellido(respuesta.getDocApellido());
        dto.setDocCorreo(respuesta.getDocCorreo());
        dto.setDocRol(respuesta.getDocRol());
        return dto;
    }

    private DocenteEntity convertirAEntity (DocenteDTO dto){
        DocenteEntity entity = new DocenteEntity();

        entity.setDocNombre(dto.getDocNombre());
        entity.setDocApellido(dto.getDocApellido());
        entity.setDocCorreo(dto.getDocCorreo());
        entity.setDocRol(dto.getDocRol());
        return entity;
    }

    public List<DocenteDTO> obtenerDatosDocentes(){
        List<DocenteEntity> etidades = repo.findAll();
        List<DocenteDTO> dtos = new ArrayList<>();
        for (DocenteEntity entity : etidades){
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public DocenteDTO obtenerPorId(Long id){
        Optional<DocenteEntity> entityOptional = repo.findById(id);

        if (entityOptional.isPresent()){
            return convertirADTO(entityOptional.get());
        }
        return null;
    }

    public boolean eliminarDocente(Long id){

        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
