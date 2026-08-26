package VisitasITR.API_PTC.Grado.Controller;

import VisitasITR.API_PTC.Grado.DTO.GradoDTO;
import VisitasITR.API_PTC.Grado.Services.GradoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grados")
@RequiredArgsConstructor
public class GradoController {

    private final GradoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradoDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Grados obtenidos", service.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Grado obtenido", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GradoDTO>> crear(@Valid @RequestBody GradoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Grado creado", service.crear(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody GradoDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Grado actualizado", service.actualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Grado eliminada", null));
    }
}