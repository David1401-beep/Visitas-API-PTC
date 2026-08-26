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
// Indica que esta clase es un controlador REST.
// Recibe peticiones HTTP y devuelve respuestas, normalmente en formato JSON.
@RestController
// Define la ruta base para todos los endpoints de este controlador.
@RequestMapping("/api/v1/academicas")
// Lombok genera automáticamente un constructor con los atributos final.
// Esto permite inyectar AcademicaService sin escribir el constructor manualmente.
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
        return ResponseEntity.ok(new ApiResponse<>(true, "Registro académico eliminado exitosamente.", null));
    }
}

//¿Por qué llamas al Service desde el Controller y no directamente al Repository?
//dwRespuesta: “Porque estoy separando responsabilidades. El Controller recibe las peticiones HTTP, el Service maneja la lógica del negocio y el Repository se encarga del acceso a la base de datos. Así el código queda más ordenado y mantenible.”
