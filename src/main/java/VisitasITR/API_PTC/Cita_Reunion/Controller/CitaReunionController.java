package VisitasITR.API_PTC.Cita_Reunion.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Services.CitaReunionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citas-reuniones")
@RequiredArgsConstructor
public class CitaReunionController {

    private final CitaReunionService citaReunionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> listar() {
        List<CitaReunionDTO> lista = citaReunionService.obtenerTodas();
        ApiResponse<List<CitaReunionDTO>> respuesta = new ApiResponse<>(true, "Lista de citas obtenida exitosamente.", lista);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> obtenerPorId(@PathVariable Long id) {
        CitaReunionDTO dto = citaReunionService.obtenerPorId(id);
        ApiResponse<CitaReunionDTO> respuesta = new ApiResponse<>(true, "Cita encontrada con éxito.", dto);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CitaReunionDTO>> crear(@Valid @RequestBody CitaReunionDTO dto) {
        CitaReunionDTO nuevo = citaReunionService.guardar(dto);
        ApiResponse<CitaReunionDTO> respuesta = new ApiResponse<>(true, "Cita programada con éxito.", nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody CitaReunionDTO dto) {
        CitaReunionDTO actualizado = citaReunionService.actualizar(id, dto);
        ApiResponse<CitaReunionDTO> respuesta = new ApiResponse<>(true, "Cita actualizada completamente.", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> actualizarParcial(@PathVariable Long id, @RequestBody CitaReunionDTO dto) {
        CitaReunionDTO actualizado = citaReunionService.actualizarParcial(id, dto);
        ApiResponse<CitaReunionDTO> respuesta = new ApiResponse<>(true, "Cita actualizada parcialmente con éxito.", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        citaReunionService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cita eliminada exitosamente.", null));
    }
}