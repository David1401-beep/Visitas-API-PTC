package VisitasITR.API_PTC.Usuarios.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Usuarios.DTO.InicioSesionEncargadoRequest;
import VisitasITR.API_PTC.Usuarios.DTO.SesionEncargadoDTO;
import VisitasITR.API_PTC.Usuarios.DTO.UsuariosDTO;
import VisitasITR.API_PTC.Usuarios.Services.UsuariosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;

    @PostMapping("/inicio-sesion-encargado")
    public ResponseEntity<ApiResponse<SesionEncargadoDTO>> iniciarSesionEncargado(
            @Valid @RequestBody InicioSesionEncargadoRequest request
    ) {
        SesionEncargadoDTO sesion = usuariosService.iniciarSesionEncargado(request);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Inicio de sesión del encargado realizado con éxito.",
                sesion
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuariosDTO>>> listar() {
        List<UsuariosDTO> lista = usuariosService.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Lista de usuarios obtenida exitosamente.",
                lista
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuariosDTO>> obtenerPorId(@PathVariable Long id) {
        UsuariosDTO dto = usuariosService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Usuario encontrado con éxito.",
                dto
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuariosDTO>> crear(@Valid @RequestBody UsuariosDTO dto) {
        UsuariosDTO nuevo = usuariosService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(true, "Usuario registrado con éxito.", nuevo)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuariosDTO>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuariosDTO dto
    ) {
        UsuariosDTO actualizado = usuariosService.actualizar(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Usuario actualizado completamente con éxito.",
                actualizado
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuariosDTO>> actualizarParcial(
            @PathVariable Long id,
            @RequestBody UsuariosDTO dto
    ) {
        UsuariosDTO actualizado = usuariosService.actualizarParcial(id, dto);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Usuario actualizado parcialmente con éxito.",
                actualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        usuariosService.eliminar(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Usuario eliminado exitosamente.", null)
        );
    }
}
