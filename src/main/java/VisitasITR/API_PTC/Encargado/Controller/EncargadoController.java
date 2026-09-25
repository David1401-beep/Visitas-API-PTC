package VisitasITR.API_PTC.Encargado.Controller;

import VisitasITR.API_PTC.Response.Paginacion;
import VisitasITR.API_PTC.Response.PaginaDTO;
import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Services.EncargadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/encargados")
@RequiredArgsConstructor
public class EncargadoController {

    private final EncargadoService encargadoService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EncargadoDTO>>> listar() {
        List<EncargadoDTO> lista = encargadoService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de encargados obtenida exitosamente.", lista));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> obtenerPorId(@PathVariable Long id) {
        EncargadoDTO dto = encargadoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado encontrado con éxito.", dto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<EncargadoDTO>> guardar(@Valid @RequestBody EncargadoDTO dto) {
        EncargadoDTO nuevo = encargadoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Encargado registrado con éxito.", nuevo));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENCARGADO')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EncargadoDTO dto
    ) {
        EncargadoDTO actualizado = encargadoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado actualizado completamente con éxito.", actualizado));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENCARGADO')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody EncargadoDTO dto
    ) {
        EncargadoDTO actualizado = encargadoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado actualizado parcialmente con éxito.", actualizado));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        encargadoService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado eliminado exitosamente.", null));
    }

    //Listar paginado. Si no mandan nada trae la pagina 0 con 10 registros.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/paginado")
    public ResponseEntity<ApiResponse<PaginaDTO<EncargadoDTO>>> listarPaginado(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ApiResponse<>(true, "Encargados paginados",
                new PaginaDTO<>(encargadoService.obtenerPaginado(Paginacion.crear(page, size, sort)))));
    }
}
