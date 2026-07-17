package VisitasITR.API_PTC.Detalles_Grado.Service;

import VisitasITR.API_PTC.Detalles_Grado.DTO.Detalles_GradoDTO;
import VisitasITR.API_PTC.Detalles_Grado.Entity.Detalles_GradoEntity;
import VisitasITR.API_PTC.Detalles_Grado.Repository.Detalles_GradoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Detalles_GradoService {

    private final Detalles_GradoRepository repo;

    public Detalles_GradoService(Detalles_GradoRepository repo) {
        this.repo = repo;
    }
    public Detalles_GradoDTO nuevoDetalleGrado(Detalles_GradoDTO dto){
        Detalles_GradoEntity datosConvertidos = convertiAEntity(dto);
        Detalles_GradoEntity respuesta = repo.save(datosConvertidos);

        return convertirADTO(respuesta);
    }

    private Detalles_GradoDTO convertirADTO(Detalles_GradoEntity respuesta){
        Detalles_GradoDTO dto = new Detalles_GradoDTO();
        dto.setId(respuesta.getId());
        dto.setDetalleGrado(respuesta.getDetalleGrado());
        dto.setIdGrupo(respuesta.getIdGrupo());
        return dto;
    }

    private Detalles_GradoEntity convertiAEntity(Detalles_GradoDTO dto){
        Detalles_GradoEntity entity = new Detalles_GradoEntity();

        entity.setDetalleGrado(dto.getDetalleGrado());
        entity.setIdGrupo(dto.getIdGrupo());
        return entity;
    }

    public List<Detalles_GradoDTO> obteerDatosGrado(){
        List<Detalles_GradoEntity> entidades = repo.findAll();

        List<Detalles_GradoDTO> dtos = new ArrayList<>();
        for (Detalles_GradoEntity entity : entidades){
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }
    public Detalles_GradoDTO obtenerPorId(Long id){
        Optional<Detalles_GradoEntity> etityOpcional = repo.findById(id);

        if (etityOpcional.isPresent()){
            return convertirADTO(etityOpcional.get());
        }
        return null;
    }
    public boolean eliminarDetalleGrado(Long id){
        if (repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
