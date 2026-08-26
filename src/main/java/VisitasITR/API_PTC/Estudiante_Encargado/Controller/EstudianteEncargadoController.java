package VisitasITR.API_PTC.Estudiante_Encargado.Controller;

import VisitasITR.API_PTC.Estudiante_Encargado.DTO.EstudianteEncargadoDTO;
import VisitasITR.API_PTC.Estudiante_Encargado.Services.EstudianteEncargadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiante-encargado")
@RequiredArgsConstructor
public class EstudianteEncargadoController {

    private final EstudianteEncargadoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteEncargadoDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Relaciones obtenidas", service.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEncargadoDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Relación obtenida", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteEncargadoDTO>> crear(@Valid @RequestBody EstudianteEncargadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Relación creada", service.crear(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true,"Relacion eliminada correctamente", null));
    }
}