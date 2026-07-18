package VisitasITR.API_PTC.Academica.Controller;

import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Academica.Services.AcademicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academicas")
@RequiredArgsConstructor
public class AcademicaController {

    private final AcademicaService academicaService;

    @GetMapping
    public ResponseEntity<List<AcademicaEntity>> listar() {
        return ResponseEntity.ok(academicaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicaEntity> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(academicaService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<AcademicaEntity> crear(@Valid @RequestBody AcademicaDTO dto) {
        try {
            AcademicaEntity nuevo = academicaService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicaEntity> actualizar(@PathVariable Long id, @Valid @RequestBody AcademicaDTO dto) {
        try {
            return ResponseEntity.ok(academicaService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            academicaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
