package VisitasITR.API_PTC.Cita_Reunion.Controller;

import VisitasITR.API_PTC.Response.Paginacion;
import VisitasITR.API_PTC.Response.PaginaDTO;
import VisitasITR.API_PTC.Cita_Reunion.DTO.CitaReunionDTO;
import VisitasITR.API_PTC.Cita_Reunion.Services.CitaReunionService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/api/v1/citas-reuniones")
@RequiredArgsConstructor
public class CitaReunionController {

    private final CitaReunionService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> listar() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Citas de reunion obtenidas", service.obtenerTodos()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/por-docente/{idDocente}", "/por-empleado/{idDocente}"})
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> listarPorDocente(
            @PathVariable Long idDocente,
            @RequestParam(required = false) String estado) {

        List<CitaReunionDTO> citas = (estado == null || estado.isBlank() || "Todos".equalsIgnoreCase(estado))
                ? service.obtenerPorDocente(idDocente)
                : service.obtenerPorDocenteYEstado(idDocente, estado.toUpperCase());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Citas del docente obtenidas", citas));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<CitaReunionDTO>>> buscar(
            @RequestParam Long idDocente,
            @RequestParam(required = false, defaultValue = "") String texto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Busqueda completada", service.buscar(idDocente, texto)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cita de reunion obtenida", service.obtenerPorId(id)));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ApiResponse<CitaReunionDTO>> crear(@Valid @RequestBody CitaReunionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Cita creada con exito", service.crear(dto)));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> actualizar(
            @PathVariable Long id, @Valid @RequestBody CitaReunionDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cita actualizada", service.actualizar(id, dto)));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CitaReunionDTO>> patchEstado(
            @PathVariable Long id, @RequestBody Map<String, Object> updates) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cita actualizada", service.patchEstado(id, updates)));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cita eliminada", null));
    }

    //Listar paginado. Si no mandan nada trae la pagina 0 con 10 registros.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/paginado")
    public ResponseEntity<ApiResponse<PaginaDTO<CitaReunionDTO>>> listarPaginado(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ApiResponse<>(true, "Citas paginados",
                new PaginaDTO<>(service.obtenerPaginado(Paginacion.crear(page, size, sort)))));
    }
}
