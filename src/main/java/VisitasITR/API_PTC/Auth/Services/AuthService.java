package VisitasITR.API_PTC.Auth.Services;

import VisitasITR.API_PTC.Auth.DTO.AuthRequestDTO;
import VisitasITR.API_PTC.Auth.DTO.AuthResponseDTO;
import VisitasITR.API_PTC.Auth.Entity.AuthEntity;
import VisitasITR.API_PTC.Auth.DTO.SesionCreada;
import VisitasITR.API_PTC.Auth.Repository.AuthRepository;
import VisitasITR.API_PTC.Security.JwtService;
import VisitasITR.API_PTC.Security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private static final List<String> ROLES_WEB = List.of(
            "ADMINISTRADOR",
            "RECEPCIONISTA",
            "DOCENTE",
            "DOCENTE TÉCNICO",
            "DOCENTE TECNICO",
            "DOCENTE ACADÉMICO",
            "DOCENTE ACADEMICO"
    );

    public SesionCreada login(AuthRequestDTO request) {
        AuthEntity usuario = buscarYValidar(request);

        if (!ROLES_WEB.contains(usuario.getRol().toUpperCase(Locale.ROOT))) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Esta cuenta no tiene acceso al sitio web."
            );
        }

        return construirRespuesta(usuario, usuario.getRol());
    }

    public SesionCreada loginEncargado(AuthRequestDTO request) {
        AuthEntity estudiante = buscarYValidar(request);

        if (!"ESTUDIANTE".equalsIgnoreCase(estudiante.getRol())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Debe ingresar con el correo del estudiante."
            );
        }

        AuthEntity encargado = new AuthEntity();
        encargado.setId(estudiante.getId());
        encargado.setEmail(estudiante.getEmail());
        encargado.setRol("ENCARGADO");

        return construirRespuesta(encargado, "ENCARGADO");
    }

    // Apoyo
    private AuthEntity buscarYValidar(AuthRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        AuthEntity usuario = authRepository.findByEmailIgnoreCase(email)
                .orElseThrow(this::correoNoRegistrado);

        if (!esHashBCrypt(usuario.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta cuenta aún no tiene una contraseña cifrada. "
                            + "Solicite al administrador que la restablezca."
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw credencialesInvalidas();
        }

        return usuario;
    }

    private boolean esHashBCrypt(String password) {
        return password != null
                && password.length() == 60
                && password.startsWith("$2");
    }

    private SesionCreada construirRespuesta(AuthEntity usuario, String rol) {
        AuthResponseDTO datos = AuthResponseDTO.builder()
                .idUsuario(usuario.getId())
                .email(usuario.getEmail())
                .rol(rol)
                .expiraEnSegundos(jwtService.getExpirationSeconds())
                .build();

        return new SesionCreada(datos, jwtService.generarToken(usuario));
    }

    //Devuelve los datos del usuario que ya viene validado por el filtro.
    //No consulto la base porque todo viene en el token.
    public AuthResponseDTO perfilActual(UsuarioAutenticado usuario) {
        return AuthResponseDTO.builder()
                .idUsuario(usuario.id())
                .email(usuario.email())
                .rol(usuario.rol())
                .expiraEnSegundos(jwtService.getExpirationSeconds())
                .build();
    }

    // Si el correo no esta en la base se lo digo claro, para que la persona
    // sepa que el problema no es la contrasena sino la cuenta.
    private ResponseStatusException correoNoRegistrado() {
        return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Este correo no está registrado en el sistema."
        );
    }

    private ResponseStatusException credencialesInvalidas() {
        return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "La contraseña es incorrecta."
        );
    }
}