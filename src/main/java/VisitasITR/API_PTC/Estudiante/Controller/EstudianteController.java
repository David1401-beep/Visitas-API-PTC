package VisitasITR.API_PTC.Estudiante.Controller;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Services.EstudianteService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteDTO>>> listar() {
        List<EstudianteDTO> lista = estudianteService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de estudiantes obtenida exitosamente.",
                lista
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> obtenerPorId(@PathVariable Long id) {
        EstudianteDTO dto = estudianteService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Estudiante encontrado con éxito.",
                dto
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteDTO>> crear(@Valid @RequestBody EstudianteDTO dto) {
        EstudianteDTO nuevo = estudianteService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Estudiante registrado con éxito.", nuevo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteDTO dto
    ) {
        EstudianteDTO actualizado = estudianteService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Estudiante actualizado completamente con éxito.",
                actualizado
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody EstudianteDTO dto
    ) {
        EstudianteDTO actualizado = estudianteService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Estudiante actualizado parcialmente con éxito.",
                actualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Estudiante eliminado exitosamente.", null)
        );
    }
}