package VisitasITR.API_PTC.Seccion_Tecnica.Controller;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.Seccion_TecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Service.Seccion_TecnicaService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seccion-tecnica")
public class Seccion_TecnicaController {

    private final Seccion_TecnicaService service;

    public Seccion_TecnicaController(Seccion_TecnicaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Seccion_TecnicaDTO>> nuevaSeccionTecnica(
            @Valid @RequestBody Seccion_TecnicaDTO json) {

        try {
            Seccion_TecnicaDTO dto = service.nuevaSeccionTecnica(json);

            ApiResponse<Seccion_TecnicaDTO> respuestaBuena =
                    new ApiResponse<>(
                            true,
                            "Sección técnica registrada correctamente",
                            dto
                    );

            return new ResponseEntity<>(
                    respuestaBuena,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<Seccion_TecnicaDTO> respuestaMala =
                    new ApiResponse<>(
                            false,
                            "No se pudo registrar la sección técnica",
                            json
                    );

            return new ResponseEntity<>(
                    respuestaMala,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Seccion_TecnicaDTO>>> obtenerSeccionesTecnicas() {

        try {
            List<Seccion_TecnicaDTO> lista =
                    service.obtenerDatosSeccionesTecnicas();

            ApiResponse<List<Seccion_TecnicaDTO>> respuestaBuena =
                    new ApiResponse<>(
                            true,
                            "Datos obtenidos correctamente",
                            lista
                    );

            return new ResponseEntity<>(
                    respuestaBuena,
                    HttpStatus.OK
            );

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<List<Seccion_TecnicaDTO>> respuestaMala =
                    new ApiResponse<>(
                            false,
                            "No se pudieron obtener las secciones técnicas",
                            null
                    );

            return new ResponseEntity<>(
                    respuestaMala,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Seccion_TecnicaDTO>> obtenerSeccionPorId(
            @PathVariable Long id) {

        try {
            Seccion_TecnicaDTO dto = service.obtenerPorId(id);

            if (dto == null) {
                ApiResponse<Seccion_TecnicaDTO> respuestaNoEncontrada =
                        new ApiResponse<>(
                                false,
                                "No se encontró la sección técnica con el id: " + id,
                                null
                        );

                return new ResponseEntity<>(
                        respuestaNoEncontrada,
                        HttpStatus.NOT_FOUND
                );
            }

            ApiResponse<Seccion_TecnicaDTO> respuestaBuena =
                    new ApiResponse<>(
                            true,
                            "Sección técnica encontrada",
                            dto
                    );

            return new ResponseEntity<>(
                    respuestaBuena,
                    HttpStatus.OK
            );

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<Seccion_TecnicaDTO> respuestaMala =
                    new ApiResponse<>(
                            false,
                            "No se pudo obtener la sección técnica",
                            null
                    );

            return new ResponseEntity<>(
                    respuestaMala,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarSeccionTecnica(
            @PathVariable Long id) {

        try {
            boolean eliminada = service.eliminarSeccionTecnica(id);

            if (eliminada) {
                ApiResponse<Void> respuestaBuena =
                        new ApiResponse<>(
                                true,
                                "La sección técnica con el id: " + id + " fue eliminada",
                                null
                        );

                return new ResponseEntity<>(
                        respuestaBuena,
                        HttpStatus.OK
                );
            }

            ApiResponse<Void> respuestaNoEncontrada =
                    new ApiResponse<>(
                            false,
                            "No se encontró la sección técnica con el id: " + id,
                            null
                    );

            return new ResponseEntity<>(
                    respuestaNoEncontrada,
                    HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<Void> respuestaMala =
                    new ApiResponse<>(
                            false,
                            "No se pudo eliminar la sección técnica",
                            null
                    );

            return new ResponseEntity<>(
                    respuestaMala,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}