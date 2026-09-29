package VisitasITR.API_PTC.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

// Guarda el token en una cookie. Al ser HttpOnly el JavaScript de la
// pagina no la puede leer, asi que es mas seguro que el localStorage.
@Component
public class CookieJwt {

    public static final String NOMBRE = "visitasitr_token";

    private final boolean secure;
    private final String sameSite;
    private final long duracionSegundos;

    public CookieJwt(
            @Value("${app.cookie.secure:false}") boolean secure,
            @Value("${app.cookie.same-site:Lax}") String sameSite,
            @Value("${app.jwt.expiration-ms:900000}") long expiracionMs
    ) {
        this.secure = secure;
        this.sameSite = sameSite;
        this.duracionSegundos = expiracionMs / 1000;
    }

    public ResponseCookie crear(String token) {
        return base(token).maxAge(duracionSegundos).build();
    }

    //Para cerrar sesion: la cookie se vence y el navegador la borra.
    public ResponseCookie limpiar() {
        return base("").maxAge(0).build();
    }

    private ResponseCookie.ResponseCookieBuilder base(String valor) {
        return ResponseCookie.from(NOMBRE, valor)
                .httpOnly(true)
                // En local es false porque uso http. En produccion se pone
                // en true desde el .env.
                .secure(secure)
                // Evita que otras paginas manden la cookie por mi.
                .sameSite(sameSite)
                .path("/");
    }
}
