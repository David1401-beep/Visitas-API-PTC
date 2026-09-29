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

@Service
public class JwtService {

    private final Key signingKey;
    private final long expirationMs;

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

    public Claims obtenerClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getExpirationSeconds() {
        return expirationMs / 1000;
    }
}
