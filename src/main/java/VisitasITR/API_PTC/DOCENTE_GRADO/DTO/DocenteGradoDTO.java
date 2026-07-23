package VisitasITR.API_PTC.DOCENTE_GRADO.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocenteGradoDTO {

    private Long idDocenteGrado;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;

    @NotNull(message = "El ID del grado es obligatorio")
    private Long idGrado;

    @NotNull(message = "El año escolar es obligatorio")
    private Integer anioEscolar;
}

