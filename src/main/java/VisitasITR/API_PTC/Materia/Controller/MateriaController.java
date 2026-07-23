package VisitasITR.API_PTC.Materia.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;

import VisitasITR.API_PTC.Materia.DTO.MateriaDTO;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import VisitasITR.API_PTC.Materia.Services.MateriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MateriaEntity>>> listar() {
        ApiResponse<List<MateriaEntity>> respuesta;
        try {
            List<MateriaEntity> lista = materiaService.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("Registros obtenidos con éxito.");
                respuesta = new ApiResponse<>(true, "Lista de materias obtenida exitosamente.", lista);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" No hay datos registrados.");
                respuesta = new ApiResponse<>(false, "No se encontraron materias en la base de datos.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error interno al consultar las materias.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaEntity>> obtenerPorId(@PathVariable Long id) {
        ApiResponse<MateriaEntity> respuesta;
        try {
            MateriaEntity dto = materiaService.buscarPorId(id);

            if (dto != null) {
                System.out.println("id" + id + ": Encontrado.");
                respuesta = new ApiResponse<>(true, "Materia encontrada con éxito.", dto);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No encontrado.");
                respuesta = new ApiResponse<>(false, "No se encontró la materia con el ID: " + id, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al buscar el registro con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MateriaEntity>> crear(@Valid @RequestBody MateriaDTO dto) {
        ApiResponse<MateriaEntity> respuesta;
        try {
            MateriaEntity nuevo = materiaService.guardar(dto);

            if (nuevo != null) {
                System.out.println("Creado exitosamente.");
                respuesta = new ApiResponse<>(true, "Materia registrada con éxito.", nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                System.out.println("No se pudo crear.");
                respuesta = new ApiResponse<>(false, "No se pudo registrar la materia.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error de datos o de solicitud al crear el registro.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaEntity>> actualizar(@PathVariable Long id, @Valid @RequestBody MateriaDTO dto) {
        ApiResponse<MateriaEntity> respuesta;
        try {
            MateriaEntity actualizado = materiaService.actualizar(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Actualizado.");
                respuesta = new ApiResponse<>(true, "Materia actualizada completamente.", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" ID " + id + ": No actualizado.");
                respuesta = new ApiResponse<>(false, "No se pudo actualizar la materia.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR : " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Registro no encontrado para actualizar o datos inválidos.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDTO>> actualizarMateria(@PathVariable Long id, @RequestBody MateriaDTO dto) {
        ApiResponse<MateriaDTO> respuesta;
        try {
            MateriaDTO actualizado = materiaService.actualizarMateria(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Parcialmente actualizado.");
                respuesta = new ApiResponse<>(true, "Materia actualizada parcialmente con éxito.", actualizado);
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
    public ResponseEntity<ApiResponse<MateriaDTO>> eliminar2(@PathVariable Long id) {
        ApiResponse<MateriaDTO> respuesta;
        try {
            boolean eliminado = materiaService.eliminar2(id);

            if (eliminado) {
                System.out.println("ID " + id + ": Eliminado correctamente.");
                respuesta = new ApiResponse<>(true, "Materia eliminada exitosamente.", null);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID" + id + ": Registro no existía.");
                respuesta = new ApiResponse<>(false, "No se encontró la materia para eliminar.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al intentar eliminar la materia.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
