package VisitasITR.API_PTC.Detalle_Grado.Controller;

import VisitasITR.API_PTC.Detalle_Grado.DTO.DetalleGradoDTO;
import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;
import VisitasITR.API_PTC.Detalle_Grado.Services.DetalleGradoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles-grados")
@RequiredArgsConstructor
public class DetalleGradoController {

    private final DetalleGradoService detalleGradoService;

    @GetMapping
    public ResponseEntity<List<DetalleGradoEntity>> listar() {
        return ResponseEntity.ok(detalleGradoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleGradoEntity> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(detalleGradoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleGradoEntity> crear(@Valid @RequestBody DetalleGradoDTO dto) {
        try {
            DetalleGradoEntity nuevo = detalleGradoService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleGradoEntity> actualizar(@PathVariable Long id, @Valid @RequestBody DetalleGradoDTO dto) {
        try {
            return ResponseEntity.ok(detalleGradoService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            detalleGradoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}