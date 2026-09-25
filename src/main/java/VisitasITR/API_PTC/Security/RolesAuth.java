package VisitasITR.API_PTC.Security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.text.Normalizer;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public final class RolesAuth {

    public static final String COOKIE_TOKEN = "visitasitr_token";

    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String DOCENTE = "DOCENTE";

    private RolesAuth() {
    }

    public static String normalizar(String rol) {
        if (rol == null) {
            return "";
        }

        String sinAcentos = Normalizer.normalize(rol, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return sinAcentos.trim().toUpperCase(Locale.ROOT).replaceAll("\\s+", "_");
    }

    public static Collection<SimpleGrantedAuthority> autoridades(String rol) {
        String base = normalizar(rol);
        Set<SimpleGrantedAuthority> autoridades = new LinkedHashSet<>();

        if (base.isEmpty()) {
            return autoridades;
        }

        autoridades.add(new SimpleGrantedAuthority("ROLE_" + base));

        if (base.startsWith("DOCENTE")) {
            autoridades.add(new SimpleGrantedAuthority("ROLE_" + DOCENTE));
        }

        return autoridades;
    }
}
