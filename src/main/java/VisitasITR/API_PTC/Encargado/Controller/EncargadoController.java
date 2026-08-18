package VisitasITR.API_PTC.Encargado.Controller;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Services.EncargadoService;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/encargados")
public class EncargadoController {

    @Autowired
    private EncargadoService encargadoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EncargadoDTO>>> listar() {
        List<EncargadoDTO> lista = encargadoService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de encargados obtenida exitosamente.", lista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> obtenerPorId(@PathVariable Long id) {
        EncargadoDTO dto = encargadoService.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado encontrado.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EncargadoDTO>> guardar(@Valid @RequestBody EncargadoDTO dto) {
        EncargadoDTO nuevo = encargadoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Encargado registrado con éxito.", nuevo));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EncargadoDTO>> actualizarParcial(@PathVariable Long id, @RequestBody EncargadoDTO dto) {
        EncargadoDTO actualizado = encargadoService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado actualizado exitosamente.", actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        encargadoService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Encargado eliminado correctamente.", null));
    }
}