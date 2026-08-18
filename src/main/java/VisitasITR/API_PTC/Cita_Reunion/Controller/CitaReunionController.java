package VisitasITR.API_PTC.Cita_Reunion.Controller;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Services.CitaReunionService;
import VisitasITR.API_PTC.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citas-reuniones")
public class CitaReunionController {

    @Autowired
    private CitaReunionService citaReunionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> obtenerTodas() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de citas obtenida correctamente", citaReunionService.obtenerTodas()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Cita encontrada", citaReunionService.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CitaReunionDTO>> crear(@RequestBody CitaReunionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Cita creada correctamente", citaReunionService.guardar(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> actualizar(@PathVariable Long id, @RequestBody CitaReunionDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Cita actualizada correctamente", citaReunionService.actualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        citaReunionService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cita eliminada correctamente", null));
    }
}