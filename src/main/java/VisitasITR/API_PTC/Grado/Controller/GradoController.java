package VisitasITR.API_PTC.Grado.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;

import VisitasITR.API_PTC.Grado.DTO.GradoDTO;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import VisitasITR.API_PTC.Grado.Services.GradoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grados")
@RequiredArgsConstructor
public class GradoController {

    private final GradoService gradoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GradoEntity>>> listar() {
        ApiResponse<List<GradoEntity>> respuesta;
        try {
            List<GradoEntity> lista = gradoService.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("Registros obtenidos con éxito.");
                respuesta = new ApiResponse<>(true, "Lista de grados obtenida exitosamente.", lista);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" No hay datos registrados.");
                respuesta = new ApiResponse<>(false, "No se encontraron grados en la base de datos.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error interno al consultar los grados.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoEntity>> obtenerPorId(@PathVariable Long id) {
        ApiResponse<GradoEntity> respuesta;
        try {
            GradoEntity dto = gradoService.buscarPorId(id);

            if (dto != null) {
                System.out.println("id" + id + ": Encontrado.");
                respuesta = new ApiResponse<>(true, "Grado encontrado con éxito.", dto);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No encontrado.");
                respuesta = new ApiResponse<>(false, "No se encontró el grado con el ID: " + id, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al buscar el registro con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GradoEntity>> crear(@Valid @RequestBody GradoDTO dto) {
        ApiResponse<GradoEntity> respuesta;
        try {
            GradoEntity nuevo = gradoService.guardar(dto);

            if (nuevo != null) {
                System.out.println("Creado exitosamente.");
                respuesta = new ApiResponse<>(true, "Grado registrado con éxito.", nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                System.out.println("No se pudo crear.");
                respuesta = new ApiResponse<>(false, "No se pudo registrar el grado.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error de datos o de solicitud al crear el registro.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoEntity>> actualizar(@PathVariable Long id, @Valid @RequestBody GradoDTO dto) {
        ApiResponse<GradoEntity> respuesta;
        try {
            GradoEntity actualizado = gradoService.actualizar(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Actualizado.");
                respuesta = new ApiResponse<>(true, "Grado actualizado completamente.", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" ID " + id + ": No actualizado.");
                respuesta = new ApiResponse<>(false, "No se pudo actualizar el grado.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR : " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Registro no encontrado para actualizar o datos inválidos.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<GradoDTO>> actualizarGrado(@PathVariable Long id, @RequestBody GradoDTO dto) {
        ApiResponse<GradoDTO> respuesta;
        try {
            GradoDTO actualizado = gradoService.actualizarGrado(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Parcialmente actualizado.");
                respuesta = new ApiResponse<>(true, "Grado actualizado parcialmente con éxito.", actualizado);
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
    public ResponseEntity<ApiResponse<GradoDTO>> eliminar2(@PathVariable Long id) {
        ApiResponse<GradoDTO> respuesta;
        try {
            boolean eliminado = gradoService.eliminar2(id);

            if (eliminado) {
                System.out.println("ID " + id + ": Eliminado correctamente.");
                respuesta = new ApiResponse<>(true, "Grado eliminado exitosamente.", null);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID" + id + ": Registro no existía.");
                respuesta = new ApiResponse<>(false, "No se encontró el grado para eliminar.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al intentar eliminar el grado.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
