package VisitasITR.API_PTC.Nivel.Controller;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Service.NivelService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/nivel")
public class NivelController {

    private final NivelService service;

    public NivelController(NivelService service) {
        this.service = service;
    }

    /*
     * POST
     * Insertar un nuevo nivel
     */
    @PostMapping
    public ResponseEntity<ApiResponse<NivelDTO>> nuevoNivel(
            @Valid @RequestBody NivelDTO json) {

        try {
            NivelDTO objDTO = service.insertarDatos(json);

            if (objDTO == null) {

                ApiResponse<NivelDTO> respuesta = new ApiResponse<>(
                        false,
                        "No se pudo completar el proceso de inserción",
                        json
                );

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(respuesta);
            }

            ApiResponse<NivelDTO> respuesta = new ApiResponse<>(
                    true,
                    "Nivel ingresado exitosamente",
                    objDTO
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<NivelDTO> respuesta = new ApiResponse<>(
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
     * Obtener todos los niveles
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<NivelDTO>>> obtenerNiveles() {

        try {
            List<NivelDTO> listaDTO = service.listarTodos();

            if (!listaDTO.isEmpty()) {

                ApiResponse<List<NivelDTO>> respuestaExitosa =
                        new ApiResponse<>(
                                true,
                                "Proceso completado",
                                listaDTO
                        );

                return ResponseEntity.ok(respuestaExitosa);
            }

            ApiResponse<List<NivelDTO>> respuestaNoData =
                    new ApiResponse<>(
                            true,
                            "No hay niveles por mostrar",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(respuestaNoData);

        } catch (Exception e) {
            log.error("No se pudo obtener la lista de niveles");

            ApiResponse<List<NivelDTO>> respuestaError =
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
     * Obtener un nivel específico
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> obtenerNivelPorId(
            @PathVariable Long id) {

        try {
            NivelDTO dto = service.buscarNivel(id);

            if (dto != null) {
                log.info("Nivel encontrado con ID: " + id);

                ApiResponse<NivelDTO> respuestaExitosa =
                        new ApiResponse<>(
                                true,
                                "Nivel encontrado",
                                dto
                        );

                return ResponseEntity.ok(respuestaExitosa);
            }

            log.info("No se encontró el nivel con ID: " + id);

            ApiResponse<NivelDTO> noEncontrado =
                    new ApiResponse<>(
                            false,
                            "Nivel no encontrado",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(noEncontrado);

        } catch (Exception e) {
            log.error("Error al buscar el nivel con ID: " + id);

            ApiResponse<NivelDTO> respuestaError =
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
     * Eliminar un nivel por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> eliminarNivel(
            @PathVariable Long id) {

        try {
            boolean respuesta = service.eliminarInfo(id);

            if (respuesta) {

                ApiResponse<NivelDTO> respuestaExitosa =
                        new ApiResponse<>(
                                true,
                                "Nivel con ID: " + id
                                        + " eliminado exitosamente",
                                null
                        );

                return ResponseEntity.ok(respuestaExitosa);
            }

            ApiResponse<NivelDTO> respuestaNoRealizada =
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
                    "Error crítico al intentar eliminar el nivel con ID: "
                            + id
            );

            e.printStackTrace();

            ApiResponse<NivelDTO> respuestaError =
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
     * Actualizar un nivel por ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> actualizarNivel(
            @PathVariable Long id,
            @Valid @RequestBody NivelDTO dto) {

        NivelDTO objeto = service.actualizarInfo(id, dto);

        if (objeto == null) {

            ApiResponse<NivelDTO> respuestaNoRealizada =
                    new ApiResponse<>(
                            false,
                            "No se pudo completar el proceso de actualización",
                            null
                    );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(respuestaNoRealizada);
        }

        ApiResponse<NivelDTO> respuestaExitosa =
                new ApiResponse<>(
                        true,
                        "Proceso de actualización completado",
                        objeto
                );

        return ResponseEntity.ok(respuestaExitosa);
    }
}
