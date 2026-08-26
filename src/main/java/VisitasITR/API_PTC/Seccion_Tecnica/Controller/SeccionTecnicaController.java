package VisitasITR.API_PTC.Seccion_Tecnica.Controller;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Services.SeccionTecnicaService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secciones-tecnicas")
@RequiredArgsConstructor
public class SeccionTecnicaController {

    private final SeccionTecnicaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeccionTecnicaDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Secciones Técnicas obtenidas", service.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeccionTecnicaDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Sección Técnica obtenida", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SeccionTecnicaDTO>> crear(@Valid @RequestBody SeccionTecnicaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Sección Técnica creada", service.crear(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SeccionTecnicaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody SeccionTecnicaDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Sección Técnica actualizada", service.actualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true,"Seccion Tecnica eliminada exitosamente.", null));
    }
}