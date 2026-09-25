package VisitasITR.API_PTC.Comunicado.Controller;

import VisitasITR.API_PTC.Response.Paginacion;
import VisitasITR.API_PTC.Response.PaginaDTO;
import VisitasITR.API_PTC.Comunicado.DTO.ComunicadoDTO;
import VisitasITR.API_PTC.Comunicado.Services.ComunicadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comunicados")
@RequiredArgsConstructor
public class ComunicadoController {

    private final ComunicadoService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ComunicadoDTO>>> listar() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicados obtenidos", service.obtenerActivos()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/por-docente/{idDocente}")
    public ResponseEntity<ApiResponse<List<ComunicadoDTO>>> listarPorDocente(
            @PathVariable Long idDocente) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicados del docente obtenidos",
                        service.obtenerPorDocente(idDocente)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<ComunicadoDTO>>> buscar(
            @RequestParam(required = false, defaultValue = "") String texto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Búsqueda completada", service.buscar(texto)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComunicadoDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicado obtenido", service.obtenerPorId(id)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCENTE')")
    @PostMapping
    public ResponseEntity<ApiResponse<ComunicadoDTO>> crear(@Valid @RequestBody ComunicadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Comunicado publicado", service.crear(dto)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ComunicadoDTO>> actualizar(
            @PathVariable Long id, @Valid @RequestBody ComunicadoDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicado actualizado", service.actualizar(id, dto)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCENTE')")
    @PatchMapping("/{id}/retirar")
    public ResponseEntity<ApiResponse<ComunicadoDTO>> retirar(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Comunicado retirado", service.retirar(id)));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Comunicado eliminado", null));
    }

    //Listar paginado. Si no mandan nada trae la pagina 0 con 10 registros.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/paginado")
    public ResponseEntity<ApiResponse<PaginaDTO<ComunicadoDTO>>> listarPaginado(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ApiResponse<>(true, "Comunicados paginados",
                new PaginaDTO<>(service.obtenerPaginado(Paginacion.crear(page, size, sort)))));
    }
}
