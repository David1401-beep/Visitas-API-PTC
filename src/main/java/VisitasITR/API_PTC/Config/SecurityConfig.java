package VisitasITR.API_PTC.Config;

import VisitasITR.API_PTC.Security.JwtAuthenticationFilter;
import VisitasITR.API_PTC.Security.RespuestasSeguridad;
import VisitasITR.API_PTC.Security.RolesAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

// Aqui defino quien puede entrar a cada ruta de la API.
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    // Los catalogos los puede ver cualquiera que inicie sesion, pero solo el
    // administrador los puede cambiar.
    private static final String[] CATALOGOS = {
            "/api/v1/niveles/**",
            "/api/v1/grados/**",
            "/api/v1/materias/**",
            "/api/v1/especialidades/**",
            "/api/v1/academicas/**",
            "/api/v1/secciones-tecnicas/**",
            "/api/v1/materia-docente/**",
            "/api/v1/docente-grado/**"
    };

    // Aqui solo pido que haya iniciado sesion. Quien puede modificar cada uno
    // lo decido con @PreAuthorize en el controlador, porque cambia segun el caso
    // (un docente puede editar su ficha, pero no crear otro docente).
    private static final String[] EXPEDIENTES = {
            "/api/v1/recepcionistas/**",
            "/api/v1/docentes/**",
            "/api/v1/estudiantes/**",
            "/api/v1/encargados/**",
            "/api/v1/estudiante-encargados/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // No guardo sesion en el servidor, todo va en el token,
                // por eso no necesito la proteccion CSRF.
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(manejo -> manejo
                        .authenticationEntryPoint(RespuestasSeguridad.noAutenticado())
                        .accessDeniedHandler(RespuestasSeguridad.accesoDenegado()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Los administradores solo los maneja un administrador.
                        .requestMatchers("/api/v1/administradores/**")
                        .hasRole(RolesAuth.ADMINISTRADOR)

                        .requestMatchers(HttpMethod.GET, CATALOGOS).authenticated()
                        .requestMatchers(CATALOGOS).hasRole(RolesAuth.ADMINISTRADOR)

                        .requestMatchers(EXPEDIENTES).authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/v1/comunicados/**").authenticated()
                        .requestMatchers("/api/v1/comunicados/**")
                        .hasAnyRole(RolesAuth.DOCENTE, RolesAuth.ADMINISTRADOR)

                        .requestMatchers("/api/v1/citas-reuniones/**").authenticated()
                        .requestMatchers("/api/v1/imagenes/**").authenticated()

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
