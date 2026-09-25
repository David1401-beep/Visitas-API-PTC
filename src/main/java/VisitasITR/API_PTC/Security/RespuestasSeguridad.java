package VisitasITR.API_PTC.Security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public final class RespuestasSeguridad {

    private RespuestasSeguridad() {
    }

    public static AuthenticationEntryPoint noAutenticado() {
        return (request, response, excepcion) -> escribir(
                response,
                HttpStatus.UNAUTHORIZED,
                "Debe iniciar sesion para acceder a este recurso."
        );
    }

    public static AccessDeniedHandler accesoDenegado() {
        return (request, response, excepcion) -> escribir(
                response,
                HttpStatus.FORBIDDEN,
                "Su rol no tiene permisos sobre este recurso."
        );
    }

    private static void escribir(HttpServletResponse response, HttpStatus estado, String mensaje)
            throws IOException {

        response.setStatus(estado.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                "{\"success\":false,\"message\":\"" + mensaje + "\",\"data\":null}"
        );
    }
}
