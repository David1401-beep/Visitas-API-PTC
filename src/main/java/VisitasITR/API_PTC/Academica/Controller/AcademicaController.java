package VisitasITR.API_PTC.Academica.Controller;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Services.AcademicaService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academicas")
@RequiredArgsConstructor
public class AcademicaController {

    private final AcademicaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AcademicaDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Académicas obtenidas", service.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Académica obtenida", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AcademicaDTO>> crear(@Valid @RequestBody AcademicaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Académica creada", service.crear(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody AcademicaDTO dto) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Académica actualizada", service.actualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true,"Academica eliminada",null));
    }
}