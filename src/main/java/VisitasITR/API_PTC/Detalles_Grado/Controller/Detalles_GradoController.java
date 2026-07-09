package VisitasITR.API_PTC.Detalles_Grado.Controller;


import VisitasITR.API_PTC.Detalles_Grado.DTO.Detalles_GradoDTO;
import VisitasITR.API_PTC.Detalles_Grado.Service.Detalles_GradoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalleGrado")
public class Detalles_GradoController {

    private final Detalles_GradoService service;

    public Detalles_GradoController(Detalles_GradoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Detalles_GradoDTO>> nuevoDetalleGrado(@Valid @RequestBody Detalles_GradoDTO json){
        try {
            Detalles_GradoDTO dto = service.nuevoDetalleGrado(json);

            ApiResponse<Detalles_GradoDTO> response = new ApiResponse<>(true, "Proceso completado", dto);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<Detalles_GradoDTO> respuestaError = new ApiResponse<>(false, "El proceso no se pudo completar", json);
            return new ResponseEntity<>(respuestaError,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Detalles_GradoDTO>>> obtenerDatosGrado(){
        try {
            List<Detalles_GradoDTO> lista = service.obteerDatosGrado();
            ApiResponse<List<Detalles_GradoDTO>> respuestaBuena = new ApiResponse<>(true, "Proceso completado", lista);
            return new ResponseEntity<>(respuestaBuena,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<List<Detalles_GradoDTO>> respuestaMala = new ApiResponse<>(false, "No se pudo obtener los datos del grado", null);
            return new ResponseEntity<>(respuestaMala,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Detalles_GradoDTO>> obtenerDatosId (@PathVariable Long id){
        try {
            Detalles_GradoDTO dto = service.obtenerPorId(id);
            ApiResponse<Detalles_GradoDTO> respuestaBuena = new ApiResponse<>(true,"Proceso completado", dto);
            return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<Detalles_GradoDTO> respuestaMala = new ApiResponse<>(false, "No se pudo obtener los datos del grado", null);
            return new ResponseEntity<>(respuestaMala,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id){
        try {
            boolean respuesta = service.eliminarDetalleGrado(id);
            if (respuesta){
                ApiResponse<Void> respuestaBuena = new ApiResponse<>(true, "El detalle del grado con el id: " + id + " ha sido eliminado", null);
                return new ResponseEntity<>(respuestaBuena, HttpStatus.NO_CONTENT);
            }
            ApiResponse<Void> respuestaNoEncontrada = new ApiResponse<>(false, "El detalle de grado con el id: " + id + " no se ha encontrado", null);
            return new ResponseEntity<>(respuestaNoEncontrada, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<Void> respuestaMala = new ApiResponse<>(false,"No se pudo eliminar el elemento seleccionado", null);
            return new ResponseEntity<>(respuestaMala,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
