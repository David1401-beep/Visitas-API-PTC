package VisitasITR.API_PTC.Nivel.Controller;

import VisitasITR.API_PTC.Nivel.DTO.NivelDTO;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Nivel.Services.NivelService;
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
    public ResponseEntity<List<NivelEntity>> listar() {
        return ResponseEntity.ok(nivelService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelEntity> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(nivelService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<NivelEntity> crear(@Valid @RequestBody NivelDTO dto) {
        try {
            NivelEntity nuevo = nivelService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NivelEntity> actualizar(@PathVariable Long id, @Valid @RequestBody NivelDTO dto) {
        try {
            return ResponseEntity.ok(nivelService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            nivelService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}