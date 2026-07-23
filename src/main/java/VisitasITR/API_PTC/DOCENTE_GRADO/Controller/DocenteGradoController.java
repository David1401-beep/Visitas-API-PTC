package VisitasITR.API_PTC.DOCENTE_GRADO.Controller;

import VisitasITR.API_PTC.DOCENTE_GRADO.DTO.DocenteGradoDTO;
import VisitasITR.API_PTC.DOCENTE_GRADO.Entity.DocenteGradoEntity;
import VisitasITR.API_PTC.DOCENTE_GRADO.Services.DocenteGradoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/docente-grados")
@RequiredArgsConstructor
public class DocenteGradoController {

    private final DocenteGradoService docenteGradoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            List<DocenteGradoEntity> lista = docenteGradoService.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("Registros obtenidos con éxito.");
                respuesta.put("!!", "Lista de docente-grado obtenida exitosamente.");
                respuesta.put("datos", lista);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" No hay datos registrados.");
                respuesta.put("!!", "No se encontraron registros de docente-grado en la base de datos.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            respuesta.put("!!", "Error interno al consultar los docente-grados.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            DocenteGradoEntity dto = docenteGradoService.buscarPorId(id);

            if (dto != null) {
                System.out.println("id" + id + ": Encontrado.");
                respuesta.put("!!", "Registro de docente-grado encontrado con éxito.");
                respuesta.put("datos", dto);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No encontrado.");
                respuesta.put("!!", "No se encontró el registro con el ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            respuesta.put("!!", "Error al buscar el registro con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody DocenteGradoDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            DocenteGradoEntity nuevo = docenteGradoService.guardar(dto);

            if (nuevo != null) {
                System.out.println("Creado exitosamente.");
                respuesta.put("!!", "Docente-grado registrado con éxito.");
                respuesta.put("datos", nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                System.out.println("No se pudo crear.");
                respuesta.put("!!", "No se pudo registrar el docente-grado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            respuesta.put("!!", "Error de datos o de solicitud al crear el registro.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody DocenteGradoDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            DocenteGradoEntity actualizado = docenteGradoService.actualizar(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Actualizado.");
                respuesta.put("!!", "Docente-grado actualizado completamente.");
                respuesta.put("datos", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" ID " + id + ": No actualizado.");
                respuesta.put("!!", "No se pudo actualizar el docente-grado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR : " + e.getMessage());
            respuesta.put("!!", "Registro no encontrado para actualizar o datos inválidos.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarDocenteGrado(@PathVariable Long id, @RequestBody DocenteGradoDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            DocenteGradoDTO actualizado = docenteGradoService.actualizarDocenteGrado(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Parcialmente actualizado.");
                respuesta.put("!!", "Docente-grado actualizado parcialmente con éxito.");
                respuesta.put("datos", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No se pudo actualizar.");
                respuesta.put("!!", "No se pudo realizar la actualización parcial.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta.put("!!", "Ocurrió un error o el ID no existe en el sistema.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar2(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            boolean eliminado = docenteGradoService.eliminar2(id);

            if (eliminado) {
                System.out.println("ID " + id + ": Eliminado correctamente.");
                respuesta.put("!!", "Docente-grado eliminado exitosamente.");
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID" + id + ": Registro no existía.");
                respuesta.put("mensaje", "No se encontró el registro para eliminar.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta.put("mensaje", "Error al intentar eliminar el docente-grado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}