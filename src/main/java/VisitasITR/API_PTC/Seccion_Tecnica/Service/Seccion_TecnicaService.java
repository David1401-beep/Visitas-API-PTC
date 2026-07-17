package VisitasITR.API_PTC.Seccion_Tecnica.Service;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.Seccion_TecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.Seccion_TecnicaEntity;
import VisitasITR.API_PTC.Seccion_Tecnica.Repository.Seccion_TecnicaRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Seccion_TecnicaService {

    private final Seccion_TecnicaRepository repo;
    public Seccion_TecnicaService(Seccion_TecnicaRepository repo) {
        this.repo = repo;
    }
    public Seccion_TecnicaDTO nuevaSeccionTecnica(Seccion_TecnicaDTO dto) {
        Seccion_TecnicaEntity datosConvertidos = convertirAEntity(dto);
        Seccion_TecnicaEntity respuesta = repo.save(datosConvertidos);
        return convertirADTO(respuesta);
    }
    private Seccion_TecnicaDTO convertirADTO(Seccion_TecnicaEntity respuesta) {
        Seccion_TecnicaDTO dto = new Seccion_TecnicaDTO();

        dto.setIdTecnica(respuesta.getIdTecnica());
        dto.setTecnica(respuesta.getTecnica());
        return dto;
    }
    private Seccion_TecnicaEntity convertirAEntity(Seccion_TecnicaDTO dto) {
        Seccion_TecnicaEntity entity = new Seccion_TecnicaEntity();
        entity.setTecnica(dto.getTecnica());
        return entity;
    }
    public List<Seccion_TecnicaDTO> obtenerDatosSeccionesTecnicas() {
        List<Seccion_TecnicaEntity> entidades = repo.findAll();
        List<Seccion_TecnicaDTO> dtos = new ArrayList<>();
        for (Seccion_TecnicaEntity entity : entidades) {
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }
    public Seccion_TecnicaDTO obtenerPorId(Long id) {
        Optional<Seccion_TecnicaEntity> entidadOpcional = repo.findById(id);
        if (entidadOpcional.isPresent()) {
            return convertirADTO(entidadOpcional.get());
        }
        return null;
    }
    public boolean eliminarSeccionTecnica(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}