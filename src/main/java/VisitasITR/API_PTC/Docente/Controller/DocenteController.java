package VisitasITR.API_PTC.Docente.Controller;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Service.DocenteService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docente")
public class DocenteController {

    private final DocenteService service;

    public DocenteController(DocenteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DocenteDTO>> nuevoDocente(@Valid @RequestBody DocenteDTO json){
        try {
            DocenteDTO dto = service.nuevoDocente(json);
            ApiResponse<DocenteDTO> response = new ApiResponse<>(true, "Proceso completado", dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<DocenteDTO> respuestaError = new ApiResponse<>(false, "El proceso de inserccion no se pudo completar", json);
            return new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DocenteDTO>>> obtenerDatosDocente(){
        try {
            List<DocenteDTO> listaDocente = service.obtenerDatosDocentes();
            ApiResponse<List<DocenteDTO>> respuestaExitosa = new ApiResponse<>(true, "Proceso completado", listaDocente);
            return new ResponseEntity<>(respuestaExitosa, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<List<DocenteDTO>> respuestaError = new ApiResponse<>(false, "No se pudo obtener los datos del docente", null);
            return new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocenteDTO>> obtenerPorId(@PathVariable Long id){
        try {
            DocenteDTO dto = service.obtenerPorId(id);
            if (dto != null){
                ApiResponse<DocenteDTO> respuestaExitosa = new ApiResponse<>(true, "Proceso completado", dto);
                return new ResponseEntity<>(respuestaExitosa, HttpStatus.OK);
            }else {
                ApiResponse<DocenteDTO> respuestaNoEncontrada = new ApiResponse<>(false, "No se encontro el docente con id: " + id, null);
                return new ResponseEntity<>(respuestaNoEncontrada, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<DocenteDTO> respuestaError = new ApiResponse<>(false, "Error al buscar docente", null);
            return new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDocente(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarDocente(id);
            if (respuesta){
                ApiResponse<Void> respuestaExitosa = new ApiResponse<>(true, "El docente con id: " + id + " ha sido eliminado", null);
                return new ResponseEntity<>(respuestaExitosa, HttpStatus.NO_CONTENT);
            }
            ApiResponse<Void> respuestaErronea = new ApiResponse<>(false, "El docente con id: " + id + " no se ha podido encontrar", null);
            return new ResponseEntity<>(respuestaErronea, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<Void> respuestaError = new ApiResponse<>(false, "No se pudo eliminar el docente seleccionado", null);
            return new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
