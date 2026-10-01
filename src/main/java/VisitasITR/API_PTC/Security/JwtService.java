package VisitasITR.API_PTC.Security;

import VisitasITR.API_PTC.Auth.Entity.AuthEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

// Crea y revisa los tokens JWT del inicio de sesion. Duran poco (15 min) porque
// un token no se puede anular: cerrar sesion solo borra la cookie.
@Service
public class JwtService {

    private final Key signingKey;
    private final long expirationMs;

    // Lee JWT_SECRET del .env (o de Heroku). Si tiene menos de 32 caracteres la API no
    // arranca, y si se cambia, todos tienen que volver a iniciar sesion.
    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms:900000}") long expirationMs // Predeterminado: 15 mins (900,000 ms)
    ) {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);

        if (secretBytes.length < 32) {
            throw new IllegalStateException("JWT_SECRET debe contener al menos 32 caracteres.");
        }

        this.signingKey = Keys.hmacShaKeyFor(secretBytes);
        this.expirationMs = expirationMs;
    }

    // Arma el token al iniciar sesion con el correo, el rol, el id y el vencimiento.
    // Va firmado pero no cifrado (cualquiera puede leerlo), por eso no lleva la contrasena.
    public String generarToken(AuthEntity usuario) {
        Instant ahora = Instant.now();
        var builder = Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("rol", usuario.getRol())
                .setIssuedAt(Date.from(ahora))
                .setExpiration(Date.from(ahora.plusMillis(expirationMs)))
                .signWith(signingKey, SignatureAlgorithm.HS256);

        if (usuario.getId() != null) {
            builder.claim("idUsuario", usuario.getId());
        }

        return builder.compact();
    }

    // Revisa la firma y que no este vencido, y devuelve los datos del token. Si falla
    // lanza una JwtException y JwtAuthenticationFilter hace que la API responda 401.
    public Claims obtenerClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // La duracion del token en segundos: es el expiraEnSegundos del login y de /auth/me.
    public long getExpirationSeconds() {
        return expirationMs / 1000;
    }
}
