package VisitasITR.API_PTC.Config;

import VisitasITR.API_PTC.Response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorExcepciones {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> validacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, "Hay campos con datos invalidos.", errores)
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> estado(ResponseStatusException ex) {
        String mensaje = ex.getReason() != null ? ex.getReason() : "No fue posible completar la operacion.";

        return ResponseEntity.status(ex.getStatusCode()).body(
                new ApiResponse<>(false, mensaje, null)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> integridad(DataIntegrityViolationException ex) {
        String detalle = ex.getMostSpecificCause().getMessage();
        String mensaje;

        if (detalle.contains("ORA-00001")) {
            mensaje = "Ya existe un registro con ese valor unico (correo, codigo o telefono).";
        } else if (detalle.contains("ORA-02292")) {
            mensaje = "No se puede eliminar: el registro esta siendo usado por otros datos relacionados.";
        } else if (detalle.contains("ORA-02291")) {
            mensaje = "El registro referenciado no existe. Verifique los identificadores enviados.";
        } else if (detalle.contains("ORA-01400") || detalle.contains("ORA-01407")) {
            mensaje = "Falta un dato obligatorio.";
        } else if (detalle.contains("ORA-02290")) {
            mensaje = "Un valor no cumple las reglas de la tabla. Revise los campos con opciones fijas.";
        } else {
            mensaje = "La operacion viola una restriccion de la base de datos.";
        }

        // El detalle tecnico queda en la consola de IntelliJ, no en el navegador.
        System.err.println("[Integridad] " + detalle);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponse<>(false, mensaje, null)
        );
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> general(Exception ex) {
        ex.printStackTrace();

        String mensaje = ex.getMessage() != null
                ? ex.getMessage()
                : "Ocurrio un error inesperado en el servidor.";

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>(false, mensaje, null)
        );
    }
}
