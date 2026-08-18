package VisitasITR.API_PTC.Nivel.Controller;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Services.NivelService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/niveles")
@RequiredArgsConstructor
public class NivelController {

    private final NivelService nivelService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NivelDTO>>> listar() {
        List<NivelDTO> lista = nivelService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de niveles obtenida exitosamente.",
                lista
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> obtenerPorId(@PathVariable Long id) {
        NivelDTO dto = nivelService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Nivel encontrado con éxito.",
                dto
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NivelDTO>> crear(@Valid @RequestBody NivelDTO dto) {
        NivelDTO nuevo = nivelService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Nivel registrado con éxito.", nuevo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody NivelDTO dto
    ) {
        NivelDTO actualizado = nivelService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Nivel actualizado completamente con éxito.",
                actualizado
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<NivelDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody NivelDTO dto
    ) {
        NivelDTO actualizado = nivelService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Nivel actualizado parcialmente con éxito.",
                actualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        nivelService.eliminar(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Nivel eliminado exitosamente.", null)
        );
    }
}