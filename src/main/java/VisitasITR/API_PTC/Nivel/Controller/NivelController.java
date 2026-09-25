package VisitasITR.API_PTC.Nivel.Controller;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Services.NivelService;
import VisitasITR.API_PTC.Nivel.Services.NivelService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/niveles")
@RequiredArgsConstructor
public class NivelController {

    private final NivelService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<NivelDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Niveles obtenidos", service.obtenerTodos()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Nivel obtenido", service.obtenerPorId(id)));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<NivelDTO>> crear(@Valid @RequestBody NivelDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Nivel creado", service.crear(dto)));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody NivelDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Nivel actualizado", service.actualizar(id, dto)));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Nivel eliminado", null));
    }
}