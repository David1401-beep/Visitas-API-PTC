package VisitasITR.API_PTC.Estudiante.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import VisitasITR.API_PTC.Estudiante.Services.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteEntity>>> listar() {
        ApiResponse<List<EstudianteEntity>> respuesta;
        try {
            List<EstudianteEntity> lista = estudianteService.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("Registros obtenidos con éxito.");
                respuesta = new ApiResponse<>(true, "Lista de estudiantes obtenida exitosamente.", lista);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" No hay datos registrados.");
                respuesta = new ApiResponse<>(false, "No se encontraron estudiantes en la base de datos.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error interno al consultar los estudiantes.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEntity>> obtenerPorId(@PathVariable Long id) {
        ApiResponse<EstudianteEntity> respuesta;
        try {
            EstudianteEntity dto = estudianteService.buscarPorId(id);

            if (dto != null) {
                System.out.println("id" + id + ": Encontrado.");
                respuesta = new ApiResponse<>(true, "Estudiante encontrado con éxito.", dto);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No encontrado.");
                respuesta = new ApiResponse<>(false, "No se encontró el estudiante con el ID: " + id, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al buscar el registro con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteEntity>> crear(@Valid @RequestBody EstudianteDTO dto) {
        ApiResponse<EstudianteEntity> respuesta;
        try {
            EstudianteEntity nuevo = estudianteService.guardar(dto);

            if (nuevo != null) {
                System.out.println("Creado exitosamente.");
                respuesta = new ApiResponse<>(true, "Estudiante registrado con éxito.", nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                System.out.println("No se pudo crear.");
                respuesta = new ApiResponse<>(false, "No se pudo registrar el estudiante.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error de datos o de solicitud al crear el registro.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteEntity>> actualizar(@PathVariable Long id, @Valid @RequestBody EstudianteDTO dto) {
        ApiResponse<EstudianteEntity> respuesta;
        try {
            EstudianteEntity actualizado = estudianteService.actualizar(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Actualizado.");
                respuesta = new ApiResponse<>(true, "Estudiante actualizado completamente.", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" ID " + id + ": No actualizado.");
                respuesta = new ApiResponse<>(false, "No se pudo actualizar el estudiante.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR : " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Registro no encontrado para actualizar o datos inválidos.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO dto) {
        ApiResponse<EstudianteDTO> respuesta;
        try {
            EstudianteDTO actualizado = estudianteService.actualizarEstudiante(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Parcialmente actualizado.");
                respuesta = new ApiResponse<>(true, "Estudiante actualizado parcialmente con éxito.", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No se pudo actualizar.");
                respuesta = new ApiResponse<>(false, "No se pudo realizar la actualización parcial.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Ocurrió un error o el ID no existe en el sistema.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> eliminar2(@PathVariable Long id) {
        ApiResponse<EstudianteDTO> respuesta;
        try {
            boolean eliminado = estudianteService.eliminar2(id);

            if (eliminado) {
                System.out.println("ID " + id + ": Eliminado correctamente.");
                respuesta = new ApiResponse<>(true, "Estudiante eliminado exitosamente.", null);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID" + id + ": Registro no existía.");
                respuesta = new ApiResponse<>(false, "No se encontró el estudiante para eliminar.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al intentar eliminar el estudiante.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
