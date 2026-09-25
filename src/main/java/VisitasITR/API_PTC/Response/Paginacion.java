package VisitasITR.API_PTC.Response;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Arma la paginacion con lo que viene en la URL. Le pongo un limite al
// tamano para que nadie pida la tabla completa de un solo.
public final class Paginacion {

    public static final int TAMANO_MINIMO = 5;
    public static final int TAMANO_MAXIMO = 50;
    public static final int TAMANO_DEFECTO = 10;
    public static final int PAGINA_DEFECTO = 0;

    private Paginacion() {
    }

    // El sort se manda como "campo" o "campo,desc".
    public static Pageable crear(Integer page, Integer size, String sort) {
        int pagina = page == null ? PAGINA_DEFECTO : page;
        int tamano = size == null ? TAMANO_DEFECTO : size;

        if (pagina < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El parametro 'page' no puede ser negativo.");
        }

        if (tamano < TAMANO_MINIMO || tamano > TAMANO_MAXIMO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El parametro 'size' debe estar entre " + TAMANO_MINIMO
                            + " y " + TAMANO_MAXIMO + ".");
        }

        return PageRequest.of(pagina, tamano, ordenar(sort));
    }

    private static Sort ordenar(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.unsorted();
        }

        String[] partes = sort.split(",");
        String campo = partes[0].trim();

        if (campo.isEmpty()) {
            return Sort.unsorted();
        }

        boolean descendente = partes.length > 1 && "desc".equalsIgnoreCase(partes[1].trim());

        return descendente ? Sort.by(campo).descending() : Sort.by(campo).ascending();
    }
}
