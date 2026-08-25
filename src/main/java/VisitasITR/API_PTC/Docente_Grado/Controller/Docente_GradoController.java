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
@RequestMapping("/api/v1/docente-grados")
@RequiredArgsConstructor
public class Docente_GradoController {

    private final Docente_GradoServices docenteGradoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Docente_GradoDTO>>> obtenerTodos() {
        List<Docente_GradoDTO> lista = docenteGradoService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de asignaciones docente-grado obtenida exitosamente.",
                lista
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Docente_GradoDTO>> obtenerPorId(@PathVariable Long id) {
        Docente_GradoDTO dto = docenteGradoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Asignación docente-grado encontrada con éxito.",
                dto
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Docente_GradoDTO>> guardar(@Valid @RequestBody Docente_GradoDTO dto) {
        Docente_GradoDTO nuevo = docenteGradoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Asignación docente-grado registrada con éxito.", nuevo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Docente_GradoDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Docente_GradoDTO dto
    ) {
        Docente_GradoDTO actualizado = docenteGradoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Asignación docente-grado actualizada completamente.",
                actualizado
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Docente_GradoDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody Docente_GradoDTO dto
    ) {
        Docente_GradoDTO actualizado = docenteGradoService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Asignación docente-grado actualizada parcialmente.",
                actualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        docenteGradoService.eliminar(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Asignación docente-grado eliminada exitosamente.", null)
        );
    }
}