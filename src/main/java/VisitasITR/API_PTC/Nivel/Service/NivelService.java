package VisitasITR.API_PTC.Nivel.Service;


import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Nivel.Repository.NivelRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NivelService {

    @Autowired
    private NivelRepository repo;

    public NivelDTO insertarDatos(@Valid NivelDTO jsonData) {

        // Validamos que el objeto recibido no sea nulo
        if (jsonData == null) {
            throw new IllegalArgumentException(
                    "La información del nivel no puede ser nula"
            );
        }

        try {
            // 1. Convertimos el DTO en Entity
            System.out.println("Bandera 1: Ejecución antes de conversión");

            NivelEntity entity = convertirAEntity(jsonData);

            System.out.println(
                    "Bandera 2: Ejecución después de conversión y antes de guardar"
            );

            // 2. Guardamos la Entity en la base de datos
            NivelEntity entitySave = repo.save(entity);

            System.out.println("Bandera 3: Ejecución después de guardar");

            // 3. Convertimos la Entity guardada nuevamente en DTO
            return convertirADTO(entitySave);

        } catch (Exception e) {
            log.error(
                    "Error al ingresar la información del nivel: "
                            + e.getMessage()
            );

            throw new RuntimeException("Error al registrar el nivel");
        }
    }

    private NivelDTO convertirADTO(NivelEntity entitySave) {

        NivelDTO objDTO = new NivelDTO();

        objDTO.setIdNivel(entitySave.getIdNivel());
        objDTO.setNivel(entitySave.getNivel());

        return objDTO;
    }

    private NivelEntity convertirAEntity(@Valid NivelDTO jsonData) {

        NivelEntity objEntity = new NivelEntity();

        objEntity.setNivel(jsonData.getNivel());

        return objEntity;
    }

    public List<NivelDTO> listarTodos() {

        List<NivelEntity> entidades = repo.findAll();

        return entidades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public NivelDTO buscarNivel(Long id) {

        Optional<NivelEntity> entidadOpcional = repo.findById(id);

        // Si el ID existe, se convierte la Entity en DTO.
        // Si no existe, se retorna null.
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

    public NivelDTO actualizarInfo(Long id, @Valid NivelDTO dto) {

        // Paso 1: buscar el registro mediante el ID
        Optional<NivelEntity> entidadOpcional = repo.findById(id);

        // Paso 2: verificar si el registro existe
        if (entidadOpcional.isEmpty()) {
            return null;
        }

        // Paso 3: obtener la Entity encontrada
        NivelEntity entity = entidadOpcional.get();

        // Paso 4: actualizar sus datos
        entity.setNivel(dto.getNivel());

        // Paso 5: guardar los cambios
        NivelEntity entityActualizada = repo.save(entity);

        // Paso 6: convertir el resultado en DTO
        return convertirADTO(entityActualizada);
    }
}