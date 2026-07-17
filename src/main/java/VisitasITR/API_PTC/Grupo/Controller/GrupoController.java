package VisitasITR.API_PTC.Grupo.Controller;

import VisitasITR.API_PTC.Grupo.DTO.GrupoDTO;
import VisitasITR.API_PTC.Grupo.Service.GrupoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/grupo")
public class GrupoController {

    private final GrupoService service;

    public GrupoController(GrupoService service) {
        this.service = service;
    }

    /*
     * POST
     * Insertar un nuevo grupo
     */
    @PostMapping
    public ResponseEntity<ApiResponse<GrupoDTO>> nuevoGrupo(
            @Valid @RequestBody GrupoDTO json) {

        try {
            GrupoDTO objDTO = service.insertarDatos(json);

            if (objDTO == null) {

                ApiResponse<GrupoDTO> respuesta = new ApiResponse<>(
                        false,
                        "No se pudo completar el proceso de inserción",
                        json
                );

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(respuesta);
            }

            ApiResponse<GrupoDTO> respuesta = new ApiResponse<>(
                    true,
                    "Grupo ingresado exitosamente",
                    objDTO
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<GrupoDTO> respuesta = new ApiResponse<>(
                    false,
                    "Error crítico: " + e.getMessage(),
                    null
            );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(respuesta);
        }
    }

    /*
     * GET
     * Obtener todos los grupos
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<GrupoDTO>>> obtenerGrupos() {

        try {
            List<GrupoDTO> listaDTO = service.listarTodos();

            if (!listaDTO.isEmpty()) {

                ApiResponse<List<GrupoDTO>> respuestaExitosa =
                        new ApiResponse<>(
                                true,
                                "Proceso completado",
                                listaDTO
                        );

                return ResponseEntity.ok(respuestaExitosa);
            }

            ApiResponse<List<GrupoDTO>> respuestaNoData =
                    new ApiResponse<>(
                            true,
                            "No hay grupos por mostrar",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(respuestaNoData);

        } catch (Exception e) {
            log.error("No se pudo obtener la lista de grupos");

            ApiResponse<List<GrupoDTO>> respuestaError =
                    new ApiResponse<>(
                            false,
                            "No se pudo completar la búsqueda",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(respuestaError);
        }
    }

    /*
     * GET por ID
     * Obtener un grupo específico
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GrupoDTO>> obtenerGrupoPorId(
            @PathVariable Long id) {

        try {
            GrupoDTO dto = service.buscarGrupo(id);

            if (dto != null) {
                log.info("Grupo encontrado con ID: " + id);

                ApiResponse<GrupoDTO> respuestaExitosa =
                        new ApiResponse<>(
                                true,
                                "Grupo encontrado",
                                dto
                        );

                return ResponseEntity.ok(respuestaExitosa);
            }

            log.info("No se encontró el grupo con ID: " + id);

            ApiResponse<GrupoDTO> noEncontrado =
                    new ApiResponse<>(
                            false,
                            "Grupo no encontrado",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(noEncontrado);

        } catch (Exception e) {
            log.error("Error al buscar el grupo con ID: " + id);

            ApiResponse<GrupoDTO> respuestaError =
                    new ApiResponse<>(
                            false,
                            "No se pudo completar la búsqueda del ID: " + id,
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(respuestaError);
        }
    }

    /*
     * DELETE
     * Eliminar un grupo por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<GrupoDTO>> eliminarGrupo(
            @PathVariable Long id) {

        try {
            boolean respuesta = service.eliminarInfo(id);

            if (respuesta) {

                ApiResponse<GrupoDTO> respuestaExitosa =
                        new ApiResponse<>(
                                true,
                                "Grupo con ID: " + id
                                        + " eliminado exitosamente",
                                null
                        );

                return ResponseEntity.ok(respuestaExitosa);
            }

            ApiResponse<GrupoDTO> respuestaNoRealizada =
                    new ApiResponse<>(
                            false,
                            "El proceso de eliminación no se pudo completar",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(respuestaNoRealizada);

        } catch (Exception e) {
            log.error(
                    "Error crítico al intentar eliminar el grupo con ID: "
                            + id
            );

            e.printStackTrace();

            ApiResponse<GrupoDTO> respuestaError =
                    new ApiResponse<>(
                            false,
                            "Error inesperado, consulte con el administrador",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(respuestaError);
        }
    }

    /*
     * PUT
     * Actualizar un grupo por ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GrupoDTO>> actualizarGrupo(
            @PathVariable Long id,
            @Valid @RequestBody GrupoDTO dto) {

        GrupoDTO objeto = service.actualizarInfo(id, dto);

        if (objeto == null) {

            ApiResponse<GrupoDTO> respuestaNoRealizada =
                    new ApiResponse<>(
                            false,
                            "No se pudo completar el proceso de actualización",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(respuestaNoRealizada);
        }

        ApiResponse<GrupoDTO> respuestaExitosa =
                new ApiResponse<>(
                        true,
                        "Proceso de actualización completado",
                        objeto
                );

        return ResponseEntity.ok(respuestaExitosa);
    }
}
