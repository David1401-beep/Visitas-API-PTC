package VisitasITR.API_PTC.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JwtService {

    private final Key signingKey;

    public JwtService(@Value("${app.jwt.secret}") String secret) {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);

        if (secretBytes.length < 32) {
            throw new IllegalStateException("JWT_SECRET debe contener al menos 32 caracteres.");
        }

        this.signingKey = Keys.hmacShaKeyFor(secretBytes);
    }

    public Claims obtenerClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
