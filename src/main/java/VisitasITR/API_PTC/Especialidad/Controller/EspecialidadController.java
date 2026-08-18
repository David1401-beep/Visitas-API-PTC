package VisitasITR.API_PTC.Especialidad.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Especialidad.DTO.EspecialidadDTO;
import VisitasITR.API_PTC.Especialidad.Services.EspecialidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EspecialidadDTO>>> listar() {
        List<EspecialidadDTO> lista = especialidadService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de especialidades obtenida exitosamente.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadDTO>> obtenerPorId(@PathVariable Long id) {
        EspecialidadDTO dto = especialidadService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Especialidad encontrada con éxito.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EspecialidadDTO>> crear(@Valid @RequestBody EspecialidadDTO dto) {
        EspecialidadDTO nuevo = especialidadService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Especialidad registrada con éxito.", nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody EspecialidadDTO dto) {
        EspecialidadDTO actualizado = especialidadService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Especialidad actualizada completamente.", actualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EspecialidadDTO>> actualizarEspecialidad(@PathVariable Long id, @RequestBody EspecialidadDTO dto) {
        EspecialidadDTO actualizado = especialidadService.actualizarEspecialidad(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Especialidad actualizada parcialmente con éxito.", actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        especialidadService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Especialidad eliminada exitosamente.", null));
    }
}