package VisitasITR.API_PTC.Cita_Reunion.Controller;

import VisitasITR.API_PTC.Cita_Reunion.DTO.Cita_ReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Service.Cita_ReunionService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citaReunion")
public class Cita_ReunionController {
    private final Cita_ReunionService service;

    public Cita_ReunionController(Cita_ReunionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cita_ReunionDTO>> nuevaCitaReunion(@Valid @RequestBody Cita_ReunionDTO json){
        try {
            Cita_ReunionDTO dto = service.nuevaCitaReunion(json);
            ApiResponse<Cita_ReunionDTO> response = new ApiResponse<>(true, "Proceso completado", dto);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<Cita_ReunionDTO> respuestaError = new ApiResponse<>(false, "El proceso no se pudo completar", json);
            return new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cita_ReunionDTO>>> obtenerDatosCitas(){
        try {
            List<Cita_ReunionDTO> lista = service.obtenerDatosCitas();
            ApiResponse<List<Cita_ReunionDTO>> respuestaBuena = new ApiResponse<>(true, "Proceso completado", lista);
            return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<List<Cita_ReunionDTO>> respuestaMala = new ApiResponse<>(false, "No se pudieron obtener los datos de las citas", null);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cita_ReunionDTO>> obtenerDatosId (@PathVariable Long id){
        try {
            Cita_ReunionDTO dto = service.obtenerPorId(id);
            ApiResponse<Cita_ReunionDTO> respuestaBuena = new ApiResponse<>(true,"Proceso completado", dto);
            return new ResponseEntity<>(respuestaBuena, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<Cita_ReunionDTO> respuestaMala = new ApiResponse<>(false, "No se pudo obtener la cita", null);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarDatos(@PathVariable Long id) {
        try {
            boolean respuesta = service.eliminarCitaReunion(id);
            if (respuesta) {
                ApiResponse<Void> respuestaBuena = new ApiResponse<>(true, "La cita con el id: " + id + " ha sido eliminada", null);
                return new ResponseEntity<>(respuestaBuena, HttpStatus.NO_CONTENT);
            }
            ApiResponse<Void> respuestaNoEncontrada = new ApiResponse<>(false, "La cita con el id: " + id + " no se ha encontrado", null);
            return new ResponseEntity<>(respuestaNoEncontrada, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<Void> respuestaMala = new ApiResponse<>(false, "No se pudo eliminar el elemento seleccionado", null);
            return new ResponseEntity<>(respuestaMala, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
