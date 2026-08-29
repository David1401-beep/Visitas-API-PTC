package VisitasITR.API_PTC.Comunicado.Controller;

import VisitasITR.API_PTC.Comunicado.DTO.ComunicadoDTO;
import VisitasITR.API_PTC.Comunicado.Services.ComunicadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comunicados")
@RequiredArgsConstructor
public class ComunicadoController {

    private final ComunicadoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ComunicadoDTO>>> listar() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicados obtenidos", service.obtenerActivos()));
    }

    @GetMapping("/por-docente/{idDocente}")
    public ResponseEntity<ApiResponse<List<ComunicadoDTO>>> listarPorDocente(
            @PathVariable Long idDocente) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicados del docente obtenidos",
                        service.obtenerPorDocente(idDocente)));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<ComunicadoDTO>>> buscar(
            @RequestParam(required = false, defaultValue = "") String texto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Búsqueda completada", service.buscar(texto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComunicadoDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicado obtenido", service.obtenerPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ComunicadoDTO>> crear(@Valid @RequestBody ComunicadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Comunicado publicado", service.crear(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ComunicadoDTO>> actualizar(
            @PathVariable Long id, @Valid @RequestBody ComunicadoDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicado actualizado", service.actualizar(id, dto)));
    }

    @PatchMapping("/{id}/retirar")
    public ResponseEntity<ApiResponse<ComunicadoDTO>> retirar(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicado retirado", service.retirar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Comunicado eliminado", null));
    }
}