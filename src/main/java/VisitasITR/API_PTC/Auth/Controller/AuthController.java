package VisitasITR.API_PTC.Auth.Controller;

import VisitasITR.API_PTC.Response.ApiResponse;
import VisitasITR.API_PTC.Auth.DTO.AuthRequestDTO;
import VisitasITR.API_PTC.Auth.DTO.AuthResponseDTO;
import VisitasITR.API_PTC.Auth.DTO.SesionCreada;
import VisitasITR.API_PTC.Auth.Services.AuthService;
import VisitasITR.API_PTC.Security.CookieJwt;
import VisitasITR.API_PTC.Security.UsuarioAutenticado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieJwt cookieJwt;

    //Inicio de sesion del personal (admin, docente, recepcionista).
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(
            @Valid @RequestBody AuthRequestDTO request
    ) {
        return responderConCookie(
                authService.login(request), "Inicio de sesión exitoso.");
    }

    //Inicio de sesion del encargado, que entra con el correo del estudiante.
    @PostMapping("/usuarios/inicio-sesion-encargado")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> inicioSesionEncargado(
            @Valid @RequestBody AuthRequestDTO request
    ) {
        return responderConCookie(
                authService.loginEncargado(request), "Inicio de sesión de encargado exitoso.");
    }

    //La web y la app llaman aqui al abrir cada pantalla, para saber si
    //la sesion sigue activa y con que rol.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth/me")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> perfil(
            @AuthenticationPrincipal UsuarioAutenticado usuario
    ) {
        return ResponseEntity.ok(new ApiResponse<>(
                true, "Sesión activa.", authService.perfilActual(usuario)));
    }

    //Cerrar sesion: solo hay que vencer la cookie.
    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieJwt.limpiar().toString())
                .body(new ApiResponse<>(true, "Sesión cerrada.", null));
    }

    @GetMapping("/auth/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("estado", "API-AUTH activa"));
    }

    private ResponseEntity<ApiResponse<AuthResponseDTO>> responderConCookie(
            SesionCreada sesion, String mensaje
    ) {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookieJwt.crear(sesion.token()).toString())
                .body(new ApiResponse<>(true, mensaje, sesion.datos()));
    }
}
