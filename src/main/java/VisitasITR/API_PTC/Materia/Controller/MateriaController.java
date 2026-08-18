package VisitasITR.API_PTC.Materia.Controller;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Services.MateriaService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MateriaDTO>>> listar() {
        List<MateriaDTO> lista = materiaService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de materias obtenida exitosamente.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDTO>> obtenerPorId(@PathVariable Long id) {
        MateriaDTO dto = materiaService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Materia encontrada con éxito.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MateriaDTO>> crear(@Valid @RequestBody MateriaDTO dto) {
        MateriaDTO nueva = materiaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Materia registrada con éxito.", nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody MateriaDTO dto) {
        MateriaDTO actualizada = materiaService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Materia actualizada completamente.", actualizada));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDTO>> actualizarParcial(@PathVariable Long id, @RequestBody MateriaDTO dto) {
        MateriaDTO actualizada = materiaService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Materia actualizada parcialmente.", actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        materiaService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Materia eliminada correctamente.", null));
    }
}