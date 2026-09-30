package VisitasITR.API_PTC.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

// Pongo el CORS en un solo lugar para no tener que usar @CrossOrigin
// en cada controlador.
@Configuration
public class CorsConfig {

    private final List<String> origenesPermitidos;

    public CorsConfig(@Value("${app.cors.origenes:}") String origenes) {
        this.origenesPermitidos = Arrays.stream(origenes.split(","))
                .map(String::trim)
                // Le quito la barra del final. El navegador manda el origen
                // sin ella ("https://sitio.com"), asi que si en la variable
                // viene con barra no coincide con nada y bloquea todo.
                .map(origen -> origen.endsWith("/")
                        ? origen.substring(0, origen.length() - 1)
                        : origen)
                .filter(origen -> !origen.isEmpty())
                .toList();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuracion = new CorsConfiguration();

        // Solo dejo entrar a las paginas que estan en la lista del .env.
        // No puedo usar "*" porque abajo activo las credenciales y el
        // navegador no acepta las dos cosas juntas.
        configuracion.setAllowedOrigins(origenesPermitidos);
        configuracion.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuracion.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // Sin esto el navegador no manda la cookie con el token.
        configuracion.setAllowCredentials(true);
        configuracion.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**", configuracion);
        return fuente;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
