package VisitasITR.API_PTC.Academica.Controller;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Service.AcademicaService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academica")
public class AcademicaController {

    private final AcademicaService service;

    public AcademicaController(AcademicaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AcademicaDTO>> nuevaAcademica(@Valid @RequestBody AcademicaDTO json){
        try {
            AcademicaDTO dto = service.nuevaAcademica(json);
            ApiResponse<AcademicaDTO> response = new ApiResponse<>(true, "Proceso completado", dto);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<AcademicaDTO> respuestaError = new ApiResponse<>(false, "El proceso no se pudo completar", json);
            return new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AcademicaDTO>>> obtenerDatosAcademica(){
        try {
            List<AcademicaDTO> lista = service.obtenerDatosAcademica();
            ApiResponse<List<AcademicaDTO>> respuestaBuena = new ApiResponse<>(true, "Proceso completado", lista);
            return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<List<AcademicaDTO>> respuestaMala = new ApiResponse<>(false, "No se pudo obtener los datos", null);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> obtenerDatosId (@PathVariable Long id){
        try {
            AcademicaDTO dto = service.obtenerPorId(id);
            ApiResponse<AcademicaDTO> respuestaBuena = new ApiResponse<>(true,"Proceso completado", dto);
            return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<AcademicaDTO> respuestaMala = new ApiResponse<>(false, "No se pudo obtener el dato", null);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id) {
        try {
            boolean respuesta = service.eliminarAcademica(id);
            if (respuesta) {
                ApiResponse<Void> respuestaBuena = new ApiResponse<>(true, "El registro con el id: " + id + " ha sido eliminado", null);
                return new ResponseEntity<>(respuestaBuena, HttpStatus.NO_CONTENT);
            }
            ApiResponse<Void> respuestaNoEncontrada = new ApiResponse<>(false, "El registro con el id: " + id + " no se ha encontrado", null);
            return new ResponseEntity<>(respuestaNoEncontrada, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<Void> respuestaMala = new ApiResponse<>(false, "No se pudo eliminar el elemento seleccionado", null);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
