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
        ApiResponse<List<AcademicaDTO>> respuesta;
        try {
            List<AcademicaDTO> lista = academicaService.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("Registros obtenidos con éxito.");
                respuesta = new ApiResponse<>(true, "Lista de secciones académicas obtenida exitosamente.", lista);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" No hay datos registrados.");
                respuesta = new ApiResponse<>(false, "No se encontraron secciones académicas en la base de datos.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error interno al consultar las secciones académicas.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> obtenerPorId(@PathVariable Long id) {
        ApiResponse<AcademicaDTO> respuesta;
        try {
            AcademicaDTO dto = academicaService.buscarPorId(id);

            if (dto != null) {
                System.out.println("id" + id + ": Encontrado.");
                respuesta = new ApiResponse<>(true, "Sección académica encontrada con éxito.", dto);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No encontrado.");
                respuesta = new ApiResponse<>(false, "No se encontró la sección académica con el ID: " + id, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al buscar el registro con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AcademicaDTO>> crear(@Valid @RequestBody AcademicaDTO dto) {
        ApiResponse<AcademicaDTO> respuesta;
        try {
            AcademicaDTO nuevo = academicaService.guardar(dto);

            if (nuevo != null) {
                System.out.println("Creado exitosamente.");
                respuesta = new ApiResponse<>(true, "Sección académica registrada con éxito.", nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                System.out.println("No se pudo crear.");
                respuesta = new ApiResponse<>(false, "No se pudo registrar la sección académica.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error de datos o de solicitud al crear el registro.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody AcademicaDTO dto) {
        ApiResponse<AcademicaDTO> respuesta;
        try {
            AcademicaDTO actualizado = academicaService.actualizar(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Actualizado.");
                respuesta = new ApiResponse<>(true, "Sección académica actualizada completamente.", actualizado);
                return ResponseEntity.ok(respuesta); // 200 OK
            } else {
                System.out.println(" ID " + id + ": No actualizado.");
                respuesta = new ApiResponse<>(false, "No se pudo actualizar la sección académica.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR : " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Registro no encontrado para actualizar o datos inválidos.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicaDTO>> actualizarAcademica(@PathVariable Long id, @RequestBody AcademicaDTO dto) {
        ApiResponse<AcademicaDTO> respuesta;
        try {
            AcademicaDTO actualizado = academicaService.actualizarAcademica(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Parcialmente actualizado.");
                respuesta = new ApiResponse<>(true, "Sección académica actualizada parcialmente con éxito.", actualizado);
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
    public ResponseEntity<ApiResponse<AcademicaDTO>> eliminar2(@PathVariable Long id) {
        ApiResponse<AcademicaDTO> respuesta;
        try {
            boolean eliminado = academicaService.eliminar2(id);

            if (eliminado) {
                System.out.println("ID " + id + ": Eliminado correctamente.");
                respuesta = new ApiResponse<>(true, "Sección académica eliminada exitosamente.", null);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID" + id + ": Registro no existía.");
                respuesta = new ApiResponse<>(false, "No se encontró la sección académica para eliminar.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al intentar eliminar la sección académica.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
