package VisitasITR.API_PTC.Grado.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Grado.DTO.GradoDTO;
import VisitasITR.API_PTC.Grado.Services.GradoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grados")
@RequiredArgsConstructor
public class GradoController {

    private final GradoService gradoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradoDTO>>> listar() {
        List<GradoDTO> lista = gradoService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de grados obtenida exitosamente.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoDTO>> obtenerPorId(@PathVariable Long id) {
        GradoDTO dto = gradoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Grado encontrado con éxito.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GradoDTO>> crear(@Valid @RequestBody GradoDTO dto) {
        GradoDTO nuevo = gradoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Grado registrado con éxito.", nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody GradoDTO dto) {
        GradoDTO actualizado = gradoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Grado actualizado completamente.", actualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoDTO>> actualizarGrado(@PathVariable Long id, @RequestBody GradoDTO dto) {
        GradoDTO actualizado = gradoService.actualizarGrado(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Grado actualizado parcialmente con éxito.", actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        gradoService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Grado eliminado exitosamente.", null));
    }
}