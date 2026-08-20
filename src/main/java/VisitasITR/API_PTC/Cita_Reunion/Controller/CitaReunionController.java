package VisitasITR.API_PTC.Cita_Reunion.Controller;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.DTO.RespuestaEncargadoDTO;
import VisitasITR.API_PTC.Cita_Reunion.Services.CitaReunionService;
import VisitasITR.API_PTC.Response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> obtenerTodas() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de citas obtenida correctamente",
                citaReunionService.obtenerTodas()
        ));
    }

    @GetMapping("/por-empleado/{idEmpleado}")
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> obtenerPorEmpleado(
            @PathVariable Long idEmpleado
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Citas del empleado obtenidas correctamente",
                citaReunionService.obtenerPorEmpleado(idEmpleado)
        ));
    }

    @GetMapping("/por-estudiante-encargado")
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> obtenerPorEstudianteEncargado(
            @RequestParam List<Long> ids
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Convocatorias del encargado obtenidas correctamente",
                citaReunionService.obtenerPorEstudiantesEncargados(ids)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Cita encontrada",
                citaReunionService.obtenerPorId(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CitaReunionDTO>> crear(
            @Valid @RequestBody CitaReunionDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                true,
                "Cita creada correctamente",
                citaReunionService.guardar(dto)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaReunionDTO dto
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Cita actualizada correctamente",
                citaReunionService.actualizar(id, dto)
        ));
    }

    @PatchMapping("/{id}/respuesta-encargado")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> responderComoEncargado(
            @PathVariable Long id,
            @Valid @RequestBody RespuestaEncargadoDTO respuesta
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Respuesta del encargado registrada correctamente",
                citaReunionService.responderComoEncargado(id, respuesta)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        citaReunionService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Cita eliminada correctamente",
                null
        ));
    }
}
