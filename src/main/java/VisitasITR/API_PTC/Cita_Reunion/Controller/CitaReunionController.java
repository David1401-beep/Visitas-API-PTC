package VisitasITR.API_PTC.Cita_Reunion.Controller;

import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Entity.CitaReunionEntity;
import VisitasITR.API_PTC.Cita_Reunion.Services.CitaReunionService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citas-reunion")
@RequiredArgsConstructor
public class CitaReunionController {

    private final CitaReunionService citaReunionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CitaReunionEntity>>> listar() {
        return responderLista(
                citaReunionService.listarTodos(),
                "Citas obtenidas exitosamente."
        );
    }

    @GetMapping("/docente/{idDocente}")
    public ResponseEntity<ApiResponse<List<CitaReunionEntity>>> listarPorDocente(
            @PathVariable Long idDocente
    ) {
        try {
            return responderLista(
                    citaReunionService.listarPorDocente(idDocente),
                    "Citas del docente obtenidas exitosamente."
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @GetMapping("/estudiante-encargado/{idRelacion}")
    public ResponseEntity<ApiResponse<List<CitaReunionEntity>>> listarPorRelacion(
            @PathVariable Long idRelacion
    ) {
        try {
            return responderLista(
                    citaReunionService.listarPorEstudianteEncargado(idRelacion),
                    "Citas de la relación estudiante-encargado obtenidas exitosamente."
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionEntity>> obtenerPorId(
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Cita encontrada exitosamente.",
                    citaReunionService.buscarPorId(id)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CitaReunionEntity>> crear(
            @Valid @RequestBody CitaReunionDTO dto
    ) {
        try {
            CitaReunionEntity cita = citaReunionService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(true, "Cita registrada exitosamente.", cita)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionEntity>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaReunionDTO dto
    ) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Cita actualizada exitosamente.",
                    citaReunionService.actualizar(id, dto)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody CitaReunionDTO dto
    ) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Cita actualizada parcialmente.",
                    citaReunionService.actualizarParcial(id, dto)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (!citaReunionService.eliminar(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, "Cita no encontrada.", null)
            );
        }
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cita eliminada exitosamente.", null)
        );
    }

    private ResponseEntity<ApiResponse<List<CitaReunionEntity>>> responderLista(
            List<CitaReunionEntity> citas,
            String mensaje
    ) {
        if (citas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, "No se encontraron citas.", null)
            );
        }
        return ResponseEntity.ok(new ApiResponse<>(true, mensaje, citas));
    }
}
