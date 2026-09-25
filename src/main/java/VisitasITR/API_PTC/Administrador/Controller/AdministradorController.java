package VisitasITR.API_PTC.Administrador.Controller;

import VisitasITR.API_PTC.Administrador.DTO.AdministradorDTO;
import VisitasITR.API_PTC.Administrador.Services.AdministradorServices;
import VisitasITR.API_PTC.Response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorServices administradorServices;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdministradorDTO>>> listar() {
        List<AdministradorDTO> lista = administradorServices.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de administradores obtenida con éxito.", lista));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdministradorDTO>> obtenerPorId(@PathVariable Long id) {
        AdministradorDTO dto = administradorServices.obtenerPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Administrador encontrado con éxito.", dto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<AdministradorDTO>> crear(@Valid @RequestBody AdministradorDTO dto) {
        AdministradorDTO nuevo = administradorServices.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Administrador registrado exitosamente.", nuevo));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdministradorDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody AdministradorDTO dto) {
        AdministradorDTO actualizado = administradorServices.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Administrador actualizado correctamente.", actualizado));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        administradorServices.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Administrador eliminado correctamente.", null));
    }
}