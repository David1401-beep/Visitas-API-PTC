package VisitasITR.API_PTC.Seccion_Tecnica.Controller;

import VisitasITR.API_PTC.Seccion_Tecnica.DTO.SeccionTecnicaDTO;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
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
    public ResponseEntity<List<SeccionTecnicaEntity>> listar() {
        return ResponseEntity.ok(seccionTecnicaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeccionTecnicaEntity> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(seccionTecnicaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<SeccionTecnicaEntity> crear(@Valid @RequestBody SeccionTecnicaDTO dto) {
        try {
            SeccionTecnicaEntity nuevo = seccionTecnicaService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeccionTecnicaEntity> actualizar(@PathVariable Long id, @Valid @RequestBody SeccionTecnicaDTO dto) {
        try {
            return ResponseEntity.ok(seccionTecnicaService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            seccionTecnicaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}