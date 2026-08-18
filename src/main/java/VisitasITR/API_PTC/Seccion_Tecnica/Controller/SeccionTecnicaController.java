package VisitasITR.API_PTC.Seccion_Tecnica.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Services.SeccionTecnicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secciones-tecnicas")
@RequiredArgsConstructor
public class SeccionTecnicaController {

    private final SeccionTecnicaService seccionTecnicaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SeccionTecnicaDTO>>> listar() {
        List<SeccionTecnicaDTO> lista = seccionTecnicaService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de secciones técnicas obtenida exitosamente.",
                lista
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeccionTecnicaDTO>> obtenerPorId(@PathVariable Long id) {
        SeccionTecnicaDTO dto = seccionTecnicaService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Sección técnica encontrada con éxito.",
                dto
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SeccionTecnicaDTO>> crear(@Valid @RequestBody SeccionTecnicaDTO dto) {
        SeccionTecnicaDTO nuevo = seccionTecnicaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Sección técnica registrada con éxito.", nuevo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SeccionTecnicaDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SeccionTecnicaDTO dto
    ) {
        SeccionTecnicaDTO actualizado = seccionTecnicaService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Sección técnica actualizada completamente.",
                actualizado
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SeccionTecnicaDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody SeccionTecnicaDTO dto
    ) {
        SeccionTecnicaDTO actualizado = seccionTecnicaService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Sección técnica actualizada parcialmente con éxito.",
                actualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        seccionTecnicaService.eliminar(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Sección técnica eliminada exitosamente.", null)
        );
    }
}