package VisitasITR.API_PTC.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CookieJwt cookieJwt;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = obtenerToken(request);

        if (StringUtils.hasText(token)) {
            try {
                Claims claims = jwtService.obtenerClaims(token);

                String rol = claims.get("rol", String.class);
                Number id = claims.get("idUsuario", Number.class);

                // Dejo aqui los datos del token para que /auth/me los devuelva
                // sin volver a consultar la base.
                UsuarioAutenticado usuario = new UsuarioAutenticado(
                        id == null ? null : id.longValue(),
                        claims.getSubject(),
                        rol
                );

                UsernamePasswordAuthenticationToken autenticacion =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                RolesAuth.autoridades(rol)
                        );

                autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(autenticacion);

                renovarSesion(request, response, usuario);
            } catch (JwtException | IllegalArgumentException excepcion) {
                // Si el token esta malo o ya vencio, lo dejo sin sesion
                // y mas adelante se responde 401.
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    // Cada peticion con una sesion valida recibe un token nuevo con otros 15
    // minutos. Asi la sesion solo se vence despues de 15 minutos SIN actividad;
    // antes se vencia a los 15 minutos de haber entrado, aunque se estuviera
    // usando.
    //
    // En el inicio y el cierre de sesion no se renueva: el inicio ya entrega su
    // propio token, y en el cierre una cookie renovada pelearia con la que se
    // esta borrando.
    private void renovarSesion(HttpServletRequest request, HttpServletResponse response,
                               UsuarioAutenticado usuario) {
        String ruta = request.getRequestURI();

        if (ruta.endsWith("/auth/login") || ruta.endsWith("/auth/logout")
                || ruta.endsWith("/usuarios/inicio-sesion-encargado")) {
            return;
        }

        String nuevo = jwtService.generarToken(usuario.email(), usuario.rol(), usuario.id());
        response.addHeader(HttpHeaders.SET_COOKIE, cookieJwt.crear(nuevo).toString());
    }

    private String obtenerToken(HttpServletRequest request) {
        return leerCookie(request).orElseGet(() -> leerEncabezado(request));
    }

    private Optional<String> leerCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> RolesAuth.COOKIE_TOKEN.equals(cookie.getName()))
                .map(Cookie::getValue)
                .filter(StringUtils::hasText)
                .findFirst();
    }

    private String leerEncabezado(HttpServletRequest request) {
        String encabezado = request.getHeader("Authorization");

        if (StringUtils.hasText(encabezado) && encabezado.startsWith("Bearer ")) {
            return encabezado.substring(7);
        }

        return null;
    }
}
