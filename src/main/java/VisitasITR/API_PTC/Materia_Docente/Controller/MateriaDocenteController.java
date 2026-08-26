package VisitasITR.API_PTC.Materia_Docente.Controller;

import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Services.MateriaDocenteService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materia-docente")
@RequiredArgsConstructor
public class MateriaDocenteController {

    private final MateriaDocenteService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MateriaDocenteDTO>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Asignaciones obtenidas", service.obtenerTodos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Asignación obtenida", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> crear(@Valid @RequestBody MateriaDocenteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Asignación creada", service.crear(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true,"Asignatura eliminada correctamente", null));
    }
}