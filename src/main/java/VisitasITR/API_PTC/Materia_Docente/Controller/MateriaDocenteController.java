package VisitasITR.API_PTC.Materia_Docente.Controller;

import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Services.MateriaDocenteService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materia-docentes")
public class MateriaDocenteController {

    @Autowired
    private MateriaDocenteService materiaDocenteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MateriaDocenteDTO>>> listar() {
        List<MateriaDocenteDTO> lista = materiaDocenteService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista obtenida con éxito.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> obtenerPorId(@PathVariable Long id) {
        MateriaDocenteDTO dto = materiaDocenteService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registro encontrado.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> crear(@Valid @RequestBody MateriaDocenteDTO dto) {
        MateriaDocenteDTO nueva = materiaDocenteService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Asignación registrada exitosamente.", nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody MateriaDocenteDTO dto) {
        MateriaDocenteDTO actualizada = materiaDocenteService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Asignación actualizada completamente.", actualizada));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> actualizarParcial(@PathVariable Long id, @RequestBody MateriaDocenteDTO dto) {
        MateriaDocenteDTO actualizada = materiaDocenteService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Asignación actualizada parcialmente.", actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        materiaDocenteService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Asignación eliminada correctamente.", null));
    }
}