package VisitasITR.API_PTC.Materia_Docente.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;

import VisitasITR.API_PTC.Materia_Docente.DTO.MateriaDocenteDTO;
import VisitasITR.API_PTC.Materia_Docente.Entity.MateriaDocenteEntity;
import VisitasITR.API_PTC.Materia_Docente.Services.MateriaDocenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materia-docentes")
@RequiredArgsConstructor
public class MateriaDocenteController {

    private final MateriaDocenteService materiaDocenteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MateriaDocenteEntity>>> listar() {
        ApiResponse<List<MateriaDocenteEntity>> respuesta;
        try {
            List<MateriaDocenteEntity> lista = materiaDocenteService.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("Registros obtenidos con éxito.");
                respuesta = new ApiResponse<>(true, "Lista de materia-docente obtenida exitosamente.", lista);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" No hay datos registrados.");
                respuesta = new ApiResponse<>(false, "No se encontraron registros de materia-docente en la base de datos.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error interno al consultar los materia-docente.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDocenteEntity>> obtenerPorId(@PathVariable Long id) {
        ApiResponse<MateriaDocenteEntity> respuesta;
        try {
            MateriaDocenteEntity dto = materiaDocenteService.buscarPorId(id);

            if (dto != null) {
                System.out.println("id" + id + ": Encontrado.");
                respuesta = new ApiResponse<>(true, "Registro de materia-docente encontrado con éxito.", dto);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No encontrado.");
                respuesta = new ApiResponse<>(false, "No se encontró el registro con el ID: " + id, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al buscar el registro con ID: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MateriaDocenteEntity>> crear(@Valid @RequestBody MateriaDocenteDTO dto) {
        ApiResponse<MateriaDocenteEntity> respuesta;
        try {
            MateriaDocenteEntity nuevo = materiaDocenteService.guardar(dto);

            if (nuevo != null) {
                System.out.println("Creado exitosamente.");
                respuesta = new ApiResponse<>(true, "Materia-docente registrado con éxito.", nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                System.out.println("No se pudo crear.");
                respuesta = new ApiResponse<>(false, "No se pudo registrar el materia-docente.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error de datos o de solicitud al crear el registro.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDocenteEntity>> actualizar(@PathVariable Long id, @Valid @RequestBody MateriaDocenteDTO dto) {
        ApiResponse<MateriaDocenteEntity> respuesta;
        try {
            MateriaDocenteEntity actualizado = materiaDocenteService.actualizar(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Actualizado.");
                respuesta = new ApiResponse<>(true, "Materia-docente actualizado completamente.", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" ID " + id + ": No actualizado.");
                respuesta = new ApiResponse<>(false, "No se pudo actualizar el materia-docente.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR : " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Registro no encontrado para actualizar o datos inválidos.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> actualizarMateriaDocente(@PathVariable Long id, @RequestBody MateriaDocenteDTO dto) {
        ApiResponse<MateriaDocenteDTO> respuesta;
        try {
            MateriaDocenteDTO actualizado = materiaDocenteService.actualizarMateriaDocente(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Parcialmente actualizado.");
                respuesta = new ApiResponse<>(true, "Materia-docente actualizado parcialmente con éxito.", actualizado);
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
    public ResponseEntity<ApiResponse<MateriaDocenteDTO>> eliminar2(@PathVariable Long id) {
        ApiResponse<MateriaDocenteDTO> respuesta;
        try {
            boolean eliminado = materiaDocenteService.eliminar2(id);

            if (eliminado) {
                System.out.println("ID " + id + ": Eliminado correctamente.");
                respuesta = new ApiResponse<>(true, "Materia-docente eliminado exitosamente.", null);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID" + id + ": Registro no existía.");
                respuesta = new ApiResponse<>(false, "No se encontró el registro para eliminar.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta = new ApiResponse<>(false, "Error al intentar eliminar el materia-docente.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
