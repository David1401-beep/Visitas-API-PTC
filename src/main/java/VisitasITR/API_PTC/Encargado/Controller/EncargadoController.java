package VisitasITR.API_PTC.Encargado.Controller;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Services.EncargadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/encargados")
@RequiredArgsConstructor
public class EncargadoController {

    private final EncargadoService encargadoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EncargadoDTO>>> listar() {
        List<EncargadoDTO> lista = encargadoService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de encargados obtenida exitosamente.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> obtenerPorId(@PathVariable Long id) {
        EncargadoDTO dto = encargadoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado encontrado con éxito.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EncargadoDTO>> guardar(@Valid @RequestBody EncargadoDTO dto) {
        EncargadoDTO nuevo = encargadoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Encargado registrado con éxito.", nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EncargadoDTO dto
    ) {
        EncargadoDTO actualizado = encargadoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado actualizado completamente con éxito.", actualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody EncargadoDTO dto
    ) {
        EncargadoDTO actualizado = encargadoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado actualizado parcialmente con éxito.", actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        encargadoService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado eliminado exitosamente.", null));
    }
}