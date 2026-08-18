package VisitasITR.API_PTC.Academica.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Academica.DTO.AcademicaDTO;
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
    public ResponseEntity<ApiResponse<List<AcademicaDTO>>> listar() {
        List<AcademicaDTO> lista = academicaService.listarTodos();
        ApiResponse<List<AcademicaDTO>> respuesta = new ApiResponse<>(true, "Lista de secciones académicas obtenida exitosamente.", lista);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> obtenerPorId(@PathVariable Long id) {
        AcademicaDTO dto = academicaService.buscarPorId(id);
        ApiResponse<AcademicaDTO> respuesta = new ApiResponse<>(true, "Sección académica encontrada con éxito.", dto);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AcademicaDTO>> crear(@Valid @RequestBody AcademicaDTO dto) {
        AcademicaDTO nuevo = academicaService.guardar(dto);
        ApiResponse<AcademicaDTO> respuesta = new ApiResponse<>(true, "Sección académica registrada con éxito.", nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody AcademicaDTO dto) {
        AcademicaDTO actualizado = academicaService.actualizar(id, dto);
        ApiResponse<AcademicaDTO> respuesta = new ApiResponse<>(true, "Sección académica actualizada completamente.", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> actualizarParcial(@PathVariable Long id, @RequestBody AcademicaDTO dto) {
        AcademicaDTO actualizado = academicaService.actualizarAcademica(id, dto);
        ApiResponse<AcademicaDTO> respuesta = new ApiResponse<>(true, "Sección académica actualizada parcialmente con éxito.", actualizado);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        academicaService.eliminar(id);
        ApiResponse<Void> respuesta = new ApiResponse<>(true, "Sección académica eliminada exitosamente.", null);
        return ResponseEntity.ok(respuesta);
    }
}