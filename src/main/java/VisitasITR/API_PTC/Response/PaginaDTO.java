package VisitasITR.API_PTC.Response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

//Lo que devuelvo cuando se pide una pagina: los registros y los datos
//que el frontend necesita para armar los botones de paginado.
@Getter
public class PaginaDTO<T> {

    private final List<T> contenido;
    private final int pagina;
    private final int tamano;
    private final long totalRegistros;
    private final int totalPaginas;
    private final boolean primera;
    private final boolean ultima;

    public PaginaDTO(Page<T> pagina) {
        this.contenido = pagina.getContent();
        this.pagina = pagina.getNumber();
        this.tamano = pagina.getSize();
        this.totalRegistros = pagina.getTotalElements();
        this.totalPaginas = pagina.getTotalPages();
        this.primera = pagina.isFirst();
        this.ultima = pagina.isLast();
    }
}
