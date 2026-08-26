package VisitasITR.API_PTC.Recepcionista.Controller;

import VisitasITR.API_PTC.Recepcionista.DTO.RecepcionistaDTO;
import VisitasITR.API_PTC.Recepcionista.Services.RecepcionistaServices;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recepcionistas")
@RequiredArgsConstructor
public class RecepcionistaController {

    private final RecepcionistaServices service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecepcionistaDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionistas obtenidos", service.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecepcionistaDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionista obtenido", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RecepcionistaDTO>> crear(@Valid @RequestBody RecepcionistaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Recepcionista creado", service.crear(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecepcionistaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody RecepcionistaDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionista actualizado", service.actualizar(id, dto)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<RecepcionistaDTO>> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionista actualizado parcialmente", service.patch(id, updates)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionista eliminado exitosamente.", null));
    }
}