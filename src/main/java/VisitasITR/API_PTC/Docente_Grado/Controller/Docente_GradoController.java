package VisitasITR.API_PTC.Docente_Grado.Controller;

import VisitasITR.API_PTC.Docente_Grado.DTO.Docente_GradoDTO;
import VisitasITR.API_PTC.Docente_Grado.Services.Docente_GradoServices;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docente-grado")
@RequiredArgsConstructor
public class Docente_GradoController {

    private final Docente_GradoServices service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Docente_GradoDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Asignaciones docente-grado obtenidas", service.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Docente_GradoDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Asignación obtenida", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Docente_GradoDTO>> crear(@Valid @RequestBody Docente_GradoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Asignación creada", service.crear(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true,"Asignatura eliminada correctamente", null));
    }
}