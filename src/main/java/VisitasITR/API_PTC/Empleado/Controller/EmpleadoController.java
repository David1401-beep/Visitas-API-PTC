package VisitasITR.API_PTC.Empleado.Controller;

import VisitasITR.API_PTC.Empleado.DTO.EmpleadoDTO;
import VisitasITR.API_PTC.Empleado.Services.EmpleadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmpleadoDTO>>> listar() {
        List<EmpleadoDTO> lista = empleadoService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de empleados obtenida exitosamente.",
                lista
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> obtenerPorId(@PathVariable Long id) {
        EmpleadoDTO dto = empleadoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Empleado encontrado con éxito.",
                dto
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmpleadoDTO>> crear(@Valid @RequestBody EmpleadoDTO dto) {
        EmpleadoDTO nuevo = empleadoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Empleado registrado con éxito.", nuevo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoDTO dto
    ) {
        EmpleadoDTO actualizado = empleadoService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Empleado actualizado completamente con éxito.",
                actualizado
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody EmpleadoDTO dto
    ) {
        EmpleadoDTO actualizado = empleadoService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Empleado actualizado parcialmente con éxito.",
                actualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Empleado eliminado exitosamente.", null)
        );
    }
}