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
@RequestMapping("/api/v1/estudiante-encargados")
@RequiredArgsConstructor
public class EstudianteEncargadoController {

    private final EstudianteEncargadoService estudianteEncargadoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteEncargadoDTO>>> obtenerTodos() {
        List<EstudianteEncargadoDTO> lista = estudianteEncargadoService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de asignaciones obtenida exitosamente.",
                lista
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEncargadoDTO>> obtenerPorId(@PathVariable Long id) {
        EstudianteEncargadoDTO dto = estudianteEncargadoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Asignación encontrada con éxito.",
                dto
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteEncargadoDTO>> guardar(@Valid @RequestBody EstudianteEncargadoDTO dto) {
        EstudianteEncargadoDTO nuevo = estudianteEncargadoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Asignación registrada con éxito.", nuevo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEncargadoDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteEncargadoDTO dto
    ) {
        EstudianteEncargadoDTO actualizado = estudianteEncargadoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Asignación actualizada completamente con éxito.",
                actualizado
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEncargadoDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody EstudianteEncargadoDTO dto
    ) {
        EstudianteEncargadoDTO actualizado = estudianteEncargadoService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Asignación actualizada parcialmente con éxito.",
                actualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        estudianteEncargadoService.eliminar(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Asignación eliminada exitosamente.", null)
        );
    }
}