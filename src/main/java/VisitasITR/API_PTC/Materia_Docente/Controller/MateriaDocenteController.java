package VisitasITR.API_PTC.Materia_Docente.Controller;

import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import VisitasITR.API_PTC.Materia_Docente.Services.MateriaDocenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materias-docentes")
@RequiredArgsConstructor
public class MateriaDocenteController {

    private final MateriaDocenteService materiaDocenteService;

    @GetMapping
    public ResponseEntity<List<MateriaDocenteEntity>> listar() {
        return ResponseEntity.ok(materiaDocenteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDocenteEntity> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(materiaDocenteService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<MateriaDocenteEntity> crear(@Valid @RequestBody MateriaDocenteDTO dto) {
        try {
            MateriaDocenteEntity nuevo = materiaDocenteService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaDocenteEntity> actualizar(@PathVariable Long id, @Valid @RequestBody MateriaDocenteDTO dto) {
        try {
            return ResponseEntity.ok(materiaDocenteService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            materiaDocenteService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}