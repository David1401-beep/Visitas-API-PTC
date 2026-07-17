package VisitasITR.API_PTC.Padre_Familia.Service;

import VisitasITR.API_PTC.Padre_Familia.DTO.Padre_FamiliaDTO;
import VisitasITR.API_PTC.Padre_Familia.Entity.Padre_FamiliaEntity;
import VisitasITR.API_PTC.Padre_Familia.Repository.Padre_FamiliaRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Getter
@Service
@Setter
public class Padre_FamiliaService {

    private final Padre_FamiliaRepository repo;

    public Padre_FamiliaService(Padre_FamiliaRepository repo) {
        this.repo = repo;
    }

    public Padre_FamiliaDTO nuevoPadreFamilia(Padre_FamiliaDTO dto) {
        Padre_FamiliaEntity datosConvertidos = convertirAEntity(dto);
        Padre_FamiliaEntity respuesta = repo.save(datosConvertidos);

        return convertirADTO(respuesta);
    }

    private Padre_FamiliaDTO convertirADTO(Padre_FamiliaEntity respuesta) {
        Padre_FamiliaDTO dto = new Padre_FamiliaDTO();

        dto.setIdPadre(respuesta.getIdPadre());
        dto.setNombre(respuesta.getNombre());
        dto.setApellido(respuesta.getApellido());
        dto.setTelefono(respuesta.getTelefono());

        return dto;
    }

    private Padre_FamiliaEntity convertirAEntity(Padre_FamiliaDTO dto) {
        Padre_FamiliaEntity entity = new Padre_FamiliaEntity();

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());

        return entity;
    }

    public List<Padre_FamiliaDTO> obtenerDatosPadresFamilia() {
        List<Padre_FamiliaEntity> entidades = repo.findAll();

        List<Padre_FamiliaDTO> dtos = new ArrayList<>();

        for (Padre_FamiliaEntity entity : entidades) {
            dtos.add(convertirADTO(entity));
        }

        return dtos;
    }

    public Padre_FamiliaDTO obtenerPorId(Long id) {
        Optional<Padre_FamiliaEntity> entidadOpcional = repo.findById(id);

        if (entidadOpcional.isPresent()) {
            return convertirADTO(entidadOpcional.get());
        }

        return null;
    }

    public boolean eliminarPadreFamilia(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }

        return false;
    }
}