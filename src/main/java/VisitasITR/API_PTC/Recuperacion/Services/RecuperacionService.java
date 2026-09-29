package VisitasITR.API_PTC.Recuperacion.Services;

import VisitasITR.API_PTC.Auth.Entity.AuthEntity;
import VisitasITR.API_PTC.Auth.Repository.AuthRepository;
import VisitasITR.API_PTC.Recuperacion.DTO.CambiarPasswordDTO;
import VisitasITR.API_PTC.Recuperacion.DTO.SolicitarCodigoDTO;
import VisitasITR.API_PTC.Recuperacion.DTO.VerificarCodigoDTO;
import VisitasITR.API_PTC.Recuperacion.Entity.RecuperacionEntity;
import VisitasITR.API_PTC.Recuperacion.Repository.PasswordRepository;
import VisitasITR.API_PTC.Recuperacion.Repository.RecuperacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecuperacionService {

    private final AuthRepository authRepository;
    private final RecuperacionRepository recuperacionRepository;
    private final PasswordRepository passwordRepository;
    private final CorreoRecuperacionService correoService;
    private final RegistroIntentos registroIntentos;
    private final PasswordEncoder passwordEncoder;

    private static final SecureRandom ALEATORIO = new SecureRandom();

    @Value("${app.recuperacion.minutos:10}")
    private int minutosDeVida;

    @Value("${app.recuperacion.intentos:3}")
    private int intentosMaximos;

    // Genera el codigo y lo manda por correo.
    //
    // Pase lo que pase, quien llama recibe la misma respuesta. Si contestara
    // distinto cuando el correo no existe, cualquiera podria averiguar que
    // cuentas estan registradas.
    @Transactional
    public void solicitar(SolicitarCodigoDTO peticion) {
        String correo = normalizar(peticion.getEmail());

        Optional<AuthEntity> usuario = authRepository.findByEmailIgnoreCase(correo);

        if (usuario.isEmpty()) {
            return;
        }

        // Al pedir uno nuevo, los codigos anteriores dejan de servir.
        recuperacionRepository.invalidarAnteriores(correo);

        String codigo = generarCodigo();

        RecuperacionEntity registro = new RecuperacionEntity();
        registro.setCorreo(correo);
        // Lo guardo cifrado: si alguien abre la tabla, los codigos no le sirven.
        registro.setCodigoHash(passwordEncoder.encode(codigo));
        registro.setExpira(LocalDateTime.now().plusMinutes(minutosDeVida));
        registro.setUsado(0);
        registro.setIntentos(0);
        registro.setCreado(LocalDateTime.now());

        recuperacionRepository.save(registro);

        correoService.enviarCodigo(correo, codigo);
    }

    // Solo revisa que el codigo sirva, para que la pantalla pueda avanzar
    // al paso de la contrasena nueva.
    @Transactional
    public void verificar(VerificarCodigoDTO peticion) {
        buscarCodigoValido(normalizar(peticion.getEmail()), peticion.getCodigo());
    }

    @Transactional
    public void cambiar(CambiarPasswordDTO peticion) {
        String correo = normalizar(peticion.getEmail());

        RecuperacionEntity registro = buscarCodigoValido(correo, peticion.getCodigo());

        AuthEntity usuario = authRepository.findByEmailIgnoreCase(correo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "El código no es válido o ya venció."));

        String hash = passwordEncoder.encode(peticion.getPasswordNueva());
        int filas = escribirPassword(usuario, correo, hash);

        if (filas == 0) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No fue posible actualizar la contraseña. Intente de nuevo."
            );
        }

        // Ya se uso: no sirve para un segundo cambio.
        registro.setUsado(1);
        recuperacionRepository.save(registro);
    }

    // Apoyo

    private RecuperacionEntity buscarCodigoValido(String correo, String codigo) {
        RecuperacionEntity registro = recuperacionRepository
                .findFirstByCorreoAndUsadoOrderByIdDesc(correo, 0)
                .orElseThrow(this::codigoInvalido);

        if (registro.estaVencido()) {
            throw codigoInvalido();
        }

        if (registro.getIntentos() >= intentosMaximos) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Demasiados intentos fallidos. Solicite un código nuevo."
            );
        }

        if (!passwordEncoder.matches(codigo, registro.getCodigoHash())) {
            // Se guarda aparte porque la excepcion de abajo deshace esta
            // transaccion y se perderia la cuenta.
            registroIntentos.sumarFallo(registro.getId());
            throw codigoInvalido();
        }

        return registro;
    }

    // Escribe en la tabla que corresponde, porque la vista no acepta UPDATE.
    private int escribirPassword(AuthEntity usuario, String correo, String hash) {
        String origen = usuario.getOrigen() == null
                ? ""
                : usuario.getOrigen().trim().toUpperCase(Locale.ROOT);

        return switch (origen) {
            case "ADMINISTRADOR" -> passwordRepository.actualizarAdministrador(correo, hash);
            case "DOCENTE" -> passwordRepository.actualizarDocente(correo, hash);
            case "RECEPCIONISTA" -> passwordRepository.actualizarRecepcionista(correo, hash);
            case "ESTUDIANTE" -> passwordRepository.actualizarEstudiante(correo, hash);
            default -> 0;
        };
    }

    private String generarCodigo() {
        return String.format("%06d", ALEATORIO.nextInt(1_000_000));
    }

    private String normalizar(String correo) {
        return correo == null ? "" : correo.trim().toLowerCase(Locale.ROOT);
    }

    private ResponseStatusException codigoInvalido() {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El código no es válido o ya venció.");
    }
}
