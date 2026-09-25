package VisitasITR.API_PTC.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

                UsernamePasswordAuthenticationToken autenticacion =
                        new UsernamePasswordAuthenticationToken(
                                claims.getSubject(),
                                null,
                                RolesAuth.autoridades(claims.get("rol", String.class))
                        );

                autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            } catch (JwtException | IllegalArgumentException excepcion) {
                // Si el token esta malo o ya vencio, lo dejo sin sesion
                // y mas adelante se responde 401.
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
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
