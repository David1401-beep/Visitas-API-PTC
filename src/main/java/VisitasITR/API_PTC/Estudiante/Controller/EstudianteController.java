package VisitasITR.API_PTC.Estudiante.Controller;

import VisitasITR.API_PTC.Response.Paginacion;
import VisitasITR.API_PTC.Response.PaginaDTO;
import VisitasITR.API_PTC.Estudiante.Services.EstudianteServices;
import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Services.EstudianteServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteServices estudianteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteDTO>>> listar() {
        List<EstudianteDTO> lista = estudianteService.obtenerTodos();
        ApiResponse<List<EstudianteDTO>> respuesta = new ApiResponse<>(true, "Lista de estudiantes obtenida exitosamente.", lista);
        return ResponseEntity.ok(respuesta);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> obtenerPorId(@PathVariable Long id) {
        EstudianteDTO dto = estudianteService.obtenerPorId(id);
        ApiResponse<EstudianteDTO> respuesta = new ApiResponse<>(true, "Estudiante encontrado con éxito.", dto);
        return ResponseEntity.ok(respuesta);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteDTO>> crear(@Valid @RequestBody EstudianteDTO dto) {
        EstudianteDTO nuevo = estudianteService.crear(dto);
        ApiResponse<EstudianteDTO> respuesta = new ApiResponse<>(true, "Estudiante registrado con éxito.", nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteDTO dto) {
        EstudianteDTO actualizado = estudianteService.actualizar(id, dto);
        ApiResponse<EstudianteDTO> respuesta = new ApiResponse<>(true, "Estudiante actualizado completamente.", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> actualizarParcial(@PathVariable Long id, @RequestBody EstudianteDTO dto) {
        EstudianteDTO actualizado = estudianteService.actualizar(id, dto);
        ApiResponse<EstudianteDTO> respuesta = new ApiResponse<>(true, "Estudiante actualizado parcialmente con éxito.", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registro de estudiante eliminado exitosamente.", null));
    }

    //Listar paginado. Si no mandan nada trae la pagina 0 con 10 registros.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/paginado")
    public ResponseEntity<ApiResponse<PaginaDTO<EstudianteDTO>>> listarPaginado(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort) {

        return ResponseEntity.ok(new ApiResponse<>(true, "Estudiantes paginados",
                new PaginaDTO<>(estudianteService.obtenerPaginado(Paginacion.crear(page, size, sort)))));
    }
}
