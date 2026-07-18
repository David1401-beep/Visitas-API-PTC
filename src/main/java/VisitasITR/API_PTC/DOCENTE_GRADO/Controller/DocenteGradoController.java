package VisitasITR.API_PTC.DOCENTE_GRADO.Controller;

import VisitasITR.API_PTC.DOCENTE_GRADO.DTO.DocenteGradoDTO;
import VisitasITR.API_PTC.DOCENTE_GRADO.Entity.DocenteGradoEntity;
import VisitasITR.API_PTC.DOCENTE_GRADO.Services.DocenteGradoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docentes-grados")
@RequiredArgsConstructor
public class DocenteGradoController {

    private final DocenteGradoService docenteGradoService;

    @GetMapping
    public ResponseEntity<List<DocenteGradoEntity>> listar() {
        return ResponseEntity.ok(docenteGradoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteGradoEntity> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(docenteGradoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<DocenteGradoEntity> crear(@Valid @RequestBody DocenteGradoDTO dto) {
        try {
            DocenteGradoEntity nuevo = docenteGradoService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteGradoEntity> actualizar(@PathVariable Long id, @Valid @RequestBody DocenteGradoDTO dto) {
        try {
            return ResponseEntity.ok(docenteGradoService.actualizar(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            docenteGradoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
