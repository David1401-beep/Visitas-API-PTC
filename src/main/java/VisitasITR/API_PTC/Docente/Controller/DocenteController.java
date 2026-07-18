package VisitasITR.API_PTC.Docente.Controller;

import VisitasITR.API_PTC.Docente.DTO.DocenteDTO;
import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Docente.Services.DocenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes") // Rutas en plural y versionadas
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService docenteService;


    @GetMapping
    public ResponseEntity<List<DocenteEntity>> listar() {
        return ResponseEntity.ok(docenteService.listarTodos()); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<DocenteEntity> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(docenteService.buscarPorId(id)); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }


    @PostMapping
    public ResponseEntity<DocenteEntity> crear(@Valid @RequestBody DocenteDTO dto) { // @Valid intercepta datos corruptos
        try {
            DocenteEntity nuevo = docenteService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<DocenteEntity> actualizar(@PathVariable Long id, @Valid @RequestBody DocenteDTO dto) {
        try {
            DocenteEntity actualizado = docenteService.actualizar(id, dto);
            return ResponseEntity.ok(actualizado); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            docenteService.eliminar(id);
            return ResponseEntity.noContent().build(); // 204 No Content (Ideal para borrado exitoso)
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }
}
