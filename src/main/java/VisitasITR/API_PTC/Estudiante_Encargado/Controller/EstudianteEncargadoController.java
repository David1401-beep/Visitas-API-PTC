package VisitasITR.API_PTC.Estudiante_Encargado.Controller;

import VisitasITR.API_PTC.Estudiante_Encargado.DTO.EstudianteEncargadoDTO;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Services.EstudianteEncargadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiante-encargados")
@RequiredArgsConstructor
public class EstudianteEncargadoController {

    private final EstudianteEncargadoService estudianteEncargadoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteEncargadoEntity>>> listar() {
        try {
            List<EstudianteEncargadoEntity> lista = estudianteEncargadoService.listarTodos();
            if (lista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(
                                false,
                                "No se encontraron relaciones estudiante-encargado.",
                                null
                        )
                );
            }
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Relaciones estudiante-encargado obtenidas exitosamente.",
                    lista
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Error al consultar las relaciones.", null)
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEncargadoEntity>> obtenerPorId(
            @PathVariable Long id
    ) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Relación estudiante-encargado encontrada.",
                    estudianteEncargadoService.buscarPorId(id)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteEncargadoEntity>> crear(
            @Valid @RequestBody EstudianteEncargadoDTO dto
    ) {
        try {
            EstudianteEncargadoEntity nueva = estudianteEncargadoService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(
                            true,
                            "Relación estudiante-encargado registrada exitosamente.",
                            nueva
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEncargadoEntity>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteEncargadoDTO dto
    ) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Relación estudiante-encargado actualizada.",
                    estudianteEncargadoService.actualizar(id, dto)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEncargadoDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody EstudianteEncargadoDTO dto
    ) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(
                    true,
                    "Relación estudiante-encargado actualizada parcialmente.",
                    estudianteEncargadoService.actualizarParcial(id, dto)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            if (!estudianteEncargadoService.eliminar(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse<>(false, "Relación no encontrada.", null)
                );
            }
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Relación eliminada exitosamente.", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse<>(
                            false,
                            "No se puede eliminar la relación porque está siendo utilizada.",
                            null
                    )
            );
        }
    }
}
