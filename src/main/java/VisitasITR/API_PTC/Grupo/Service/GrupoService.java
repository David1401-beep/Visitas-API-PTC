package VisitasITR.API_PTC.Grupo.Service;

import VisitasITR.API_PTC.Grupo.DTO.GrupoDTO;
import VisitasITR.API_PTC.Grupo.Entity.GrupoEntity;
import VisitasITR.API_PTC.Grupo.Repository.GrupoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GrupoService {

    @Autowired
    private GrupoRepository repo;

    public GrupoDTO insertarDatos(@Valid GrupoDTO jsonData) {

        // Validamos que el objeto recibido no sea nulo
        if (jsonData == null) {
            throw new IllegalArgumentException(
                    "La información del grupo no puede ser nula"
            );
        }

        try {
            // Paso 1: convertir el DTO en Entity
            System.out.println("Bandera 1: Ejecución antes de conversión");

            GrupoEntity entity = convertirAEntity(jsonData);

            System.out.println(
                    "Bandera 2: Ejecución después de conversión y antes de guardar"
            );

            // Paso 2: guardar la Entity en la base de datos
            GrupoEntity entitySave = repo.save(entity);

            System.out.println("Bandera 3: Ejecución después de guardar");

            // Paso 3: convertir la Entity guardada nuevamente en DTO
            return convertirADTO(entitySave);

        } catch (Exception e) {
            log.error(
                    "Error al ingresar la información del grupo: "
                            + e.getMessage()
            );

            throw new RuntimeException("Error al registrar el grupo");
        }
    }

    private GrupoDTO convertirADTO(GrupoEntity entitySave) {

        GrupoDTO objDTO = new GrupoDTO();

        objDTO.setIdGrupo(entitySave.getIdGrupo());
        objDTO.setGrupo(entitySave.getGrupo());
        objDTO.setIdNivel(entitySave.getIdNivel());
        objDTO.setIdEspecialidad(entitySave.getIdEspecialidad());
        objDTO.setIdAcademica(entitySave.getIdAcademica());
        objDTO.setIdTecnica(entitySave.getIdTecnica());

        return objDTO;
    }

    private GrupoEntity convertirAEntity(@Valid GrupoDTO jsonData) {

        GrupoEntity objEntity = new GrupoEntity();

        objEntity.setGrupo(jsonData.getGrupo());
        objEntity.setIdNivel(jsonData.getIdNivel());
        objEntity.setIdEspecialidad(jsonData.getIdEspecialidad());
        objEntity.setIdAcademica(jsonData.getIdAcademica());
        objEntity.setIdTecnica(jsonData.getIdTecnica());

        return objEntity;
    }

    public List<GrupoDTO> listarTodos() {

        List<GrupoEntity> entidades = repo.findAll();

        return entidades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public GrupoDTO buscarGrupo(Long id) {

        Optional<GrupoEntity> entidadOpcional = repo.findById(id);

        // Si el ID existe, convierte la Entity en DTO.
        // Si no existe, retorna null.
        return entidadOpcional
                .map(this::convertirADTO)
                .orElse(null);
    }

    public boolean eliminarInfo(Long id) {

        // Paso 1: verificar si el ID existe
        if (repo.existsById(id)) {

            // Paso 2: eliminar el registro
            repo.deleteById(id);

            // Paso 3: indicar que se eliminó correctamente
            return true;
        }

        return false;
    }

    public GrupoDTO actualizarInfo(Long id, @Valid GrupoDTO dto) {

        // Paso 1: buscar el grupo mediante el ID
        Optional<GrupoEntity> entidadOpcional = repo.findById(id);

        // Paso 2: verificar si el grupo existe
        if (entidadOpcional.isEmpty()) {
            return null;
        }

        // Paso 3: obtener la Entity encontrada
        GrupoEntity entity = entidadOpcional.get();

        // Paso 4: actualizar sus datos
        entity.setGrupo(dto.getGrupo());
        entity.setIdNivel(dto.getIdNivel());
        entity.setIdEspecialidad(dto.getIdEspecialidad());
        entity.setIdAcademica(dto.getIdAcademica());
        entity.setIdTecnica(dto.getIdTecnica());

        // Paso 5: guardar los cambios
        GrupoEntity entityActualizada = repo.save(entity);

        // Paso 6: convertir el resultado en DTO
        return convertirADTO(entityActualizada);
    }
}
