package VisitasITR.API_PTC.Docente.Controller;

import VisitasITR.API_PTC.Response.Paginacion;
import VisitasITR.API_PTC.Response.PaginaDTO;
import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Services.DocenteServices;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteServices docenteServices;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DocenteDTO>>> listar() {
        List<DocenteDTO> lista = docenteServices.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de docentes obtenida con éxito.", lista));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocenteDTO>> obtenerPorId(@PathVariable Long id) {
        DocenteDTO dto = docenteServices.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Docente encontrado con éxito.", dto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<DocenteDTO>> crear(@Valid @RequestBody DocenteDTO dto) {
        DocenteDTO nuevo = docenteServices.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Docente registrado exitosamente.", nuevo));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','DOCENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocenteDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody DocenteDTO dto) {
        DocenteDTO actualizado = docenteServices.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Docente actualizado correctamente.", actualizado));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        docenteServices.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Docente eliminado correctamente.", null));
    }

    //Listar paginado. Si no mandan nada trae la pagina 0 con 10 registros.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/paginado")
    public ResponseEntity<ApiResponse<PaginaDTO<DocenteDTO>>> listarPaginado(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ApiResponse<>(true, "Docentes paginados",
                new PaginaDTO<>(docenteServices.obtenerPaginado(Paginacion.crear(page, size, sort)))));
    }
}
