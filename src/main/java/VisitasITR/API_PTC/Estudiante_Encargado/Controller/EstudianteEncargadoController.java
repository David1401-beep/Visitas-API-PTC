package VisitasITR.API_PTC.Estudiante_Encargado.Controller;

import VisitasITR.API_PTC.EstudianteEncargado.DTO.EstudianteEncargadoDTO;
import VisitasITR.API_PTC.Estudiante_Encargado.Services.EstudianteEncargadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiante-encargados")
public class EstudianteEncargadoController {

    @Autowired
    private EstudianteEncargadoService estudianteEncargadoService;

    @GetMapping
    public ResponseEntity<List<EstudianteEncargadoDTO>> obtenerTodos() {
        return ResponseEntity.ok(estudianteEncargadoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteEncargadoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteEncargadoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EstudianteEncargadoDTO> guardar(@Valid @RequestBody EstudianteEncargadoDTO dto) {
        return new ResponseEntity<>(estudianteEncargadoService.guardar(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteEncargadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}