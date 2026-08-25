package VisitasITR.API_PTC.Docente.Controller;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Services.DocenteServices;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteServices docenteServices;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DocenteDTO>>> listar() {
        List<DocenteDTO> lista = docenteServices.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de docentes obtenida con éxito.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DocenteDTO>> obtenerPorId(@PathVariable Long id) {
        DocenteDTO dto = docenteServices.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Docente encontrado con éxito.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DocenteDTO>> crear(@Valid @RequestBody DocenteDTO dto) {
        DocenteDTO nuevo = docenteServices.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Docente registrado exitosamente.", nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DocenteDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody DocenteDTO dto) {
        DocenteDTO actualizado = docenteServices.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Docente actualizado correctamente.", actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        docenteServices.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Docente eliminado correctamente.", null));
    }
}