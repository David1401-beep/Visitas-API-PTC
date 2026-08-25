package VisitasITR.API_PTC.Recepcionista.Controller;

import VisitasITR.API_PTC.Recepcionista.DTO.RecepcionistaDTO;
import VisitasITR.API_PTC.Recepcionista.Services.RecepcionistaServices;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recepcionistas")
@RequiredArgsConstructor
public class RecepcionistaController {

    private final RecepcionistaServices recepcionistaServices;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecepcionistaDTO>>> listar() {
        List<RecepcionistaDTO> lista = recepcionistaServices.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de recepcionistas obtenida con éxito.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecepcionistaDTO>> obtenerPorId(@PathVariable Long id) {
        RecepcionistaDTO dto = recepcionistaServices.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionista encontrado con éxito.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RecepcionistaDTO>> crear(@Valid @RequestBody RecepcionistaDTO dto) {
        RecepcionistaDTO nuevo = recepcionistaServices.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Recepcionista registrado exitosamente.", nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecepcionistaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody RecepcionistaDTO dto) {
        RecepcionistaDTO actualizado = recepcionistaServices.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionista actualizado correctamente.", actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        recepcionistaServices.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recepcionista eliminado correctamente.", null));
    }
}