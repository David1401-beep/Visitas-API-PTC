package VisitasITR.API_PTC.Padre_Familia.Controller;

import VisitasITR.API_PTC.Padre_Familia.DTO.Padre_FamiliaDTO;
import VisitasITR.API_PTC.Padre_Familia.Service.Padre_FamiliaService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/padre-familia")
public class Padre_FamiliaController {

    private final Padre_FamiliaService service;

    public Padre_FamiliaController(Padre_FamiliaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Padre_FamiliaDTO>> nuevoPadreFamilia(
            @Valid @RequestBody Padre_FamiliaDTO json) {

        try {
            Padre_FamiliaDTO dto = service.nuevoPadreFamilia(json);

            ApiResponse<Padre_FamiliaDTO> respuestaBuena = new ApiResponse<>(true, "Padre de familia registrado correctamente", dto);

            return new ResponseEntity<>(respuestaBuena, HttpStatus.CREATED
            );

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<Padre_FamiliaDTO> respuestaMala = new ApiResponse<>(false, "No se pudo registrar el padre de familia", json);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Padre_FamiliaDTO>>> obtenerPadresFamilia() {

        try {
            List<Padre_FamiliaDTO> lista = service.obtenerDatosPadresFamilia();

            ApiResponse<List<Padre_FamiliaDTO>> respuestaBuena = new ApiResponse<>(true, "Datos obtenidos correctamente", lista);

            return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<List<Padre_FamiliaDTO>> respuestaMala = new ApiResponse<>(false, "No se pudieron obtener los padres de familia", null);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Padre_FamiliaDTO>> obtenerPadrePorId(
            @PathVariable Long id) {

        try {
            Padre_FamiliaDTO dto = service.obtenerPorId(id);

            if (dto == null) {
                ApiResponse<Padre_FamiliaDTO> respuestaNoEncontrada = new ApiResponse<>(false, "No se encontró el padre de familia con el id: " + id, null);
                return new ResponseEntity<>(respuestaNoEncontrada, HttpStatus.NOT_FOUND);
            }

            ApiResponse<Padre_FamiliaDTO> respuestaBuena = new ApiResponse<>(true, "Padre de familia encontrado", dto);

            return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<Padre_FamiliaDTO> respuestaMala = new ApiResponse<>(false, "No se pudo obtener el padre de familia", null);

            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarPadreFamilia(
            @PathVariable Long id) {

        try {
            boolean eliminado = service.eliminarPadreFamilia(id);

            if (eliminado) {ApiResponse<Void> respuestaBuena = new ApiResponse<>(true, "El padre de familia con el id: " + id + " fue eliminado", null);
                return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);
            }
            ApiResponse<Void> respuestaNoEncontrada = new ApiResponse<>(false, "No se encontró el padre de familia con el id: " + id, null);

            return new ResponseEntity<>(respuestaNoEncontrada, HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<Void> respuestaMala = new ApiResponse<>(false, "No se pudo eliminar el padre de familia", null);

            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}