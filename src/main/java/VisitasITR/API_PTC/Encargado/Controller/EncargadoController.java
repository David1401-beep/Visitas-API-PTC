package VisitasITR.API_PTC.Encargado.Controller;

import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Encargado.Services.EncargadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/encargados")
@RequiredArgsConstructor
public class EncargadoController {

    private final EncargadoService encargadoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            List<EncargadoEntity> lista = encargadoService.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("Registros obtenidos con éxito.");
                respuesta.put("!!", "Lista de encargados obtenida exitosamente.");
                respuesta.put("datos", lista);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" No hay datos registrados.");
                respuesta.put("!!", "No se encontraron encargados en la base de datos.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            respuesta.put("!!", "Error interno al consultar los encargados.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            EncargadoEntity dto = encargadoService.buscarPorId(id);

            if (dto != null) {
                System.out.println("id" + id + ": Encontrado.");
                respuesta.put("!!", "Encargado encontrado con éxito.");
                respuesta.put("datos", dto);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID " + id + ": No encontrado.");
                respuesta.put("!!", "No se encontró el encargado con el ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            respuesta.put("!!", "Error al buscar el registro con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EncargadoDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            EncargadoEntity nuevo = encargadoService.guardar(dto);

            if (nuevo != null) {
                System.out.println("Creado exitosamente.");
                respuesta.put("!!", "Encargado registrado con éxito.");
                respuesta.put("datos", nuevo);
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            } else {
                System.out.println("No se pudo crear.");
                respuesta.put("!!", "No se pudo registrar el encargado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
            respuesta.put("!!", "Error de datos o de solicitud al crear el registro.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody EncargadoDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            EncargadoEntity actualizado = encargadoService.actualizar(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Actualizado.");
                respuesta.put("!!", "Encargado actualizado completamente.");
                respuesta.put("datos", actualizado);
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println(" ID " + id + ": No actualizado.");
                respuesta.put("!!", "No se pudo actualizar el encargado.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR : " + e.getMessage());
            respuesta.put("!!", "Registro no encontrado para actualizar o datos inválidos.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarEncargado(@PathVariable Long id, @RequestBody EncargadoDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            EncargadoDTO actualizado = encargadoService.actualizarEncargado(id, dto);

            if (actualizado != null) {
                System.out.println("ID " + id + ": Parcialmente actualizado.");
                respuesta.put("!!", "Encargado actualizado parcialmente con éxito.");
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
            boolean eliminado = encargadoService.eliminar2(id);

            if (eliminado) {
                System.out.println("ID " + id + ": Eliminado correctamente.");
                respuesta.put("!!", "Encargado eliminado exitosamente.");
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("ID" + id + ": Registro no existía.");
                respuesta.put("!!", "No se encontró el encargado para eliminar.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            System.out.println(" ERROR: " + e.getMessage());
            respuesta.put("!!", "Error al intentar eliminar el encargado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}