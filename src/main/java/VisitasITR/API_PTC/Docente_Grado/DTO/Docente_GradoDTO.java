package VisitasITR.API_PTC.Docente_Grado.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Docente_GradoDTO {
    private Long idDocenteGrado;

    @NotNull(message = "El ID de docente es obligatorio")
    private Long idDocente;
    private String nombreDocente;

    @NotNull(message = "El ID de grado es obligatorio")
    private Long idGrado;
    private String nombreGrado;

    @NotNull(message = "El año escolar es obligatorio")
    @Min(value = 2000, message = "Año no puede ser menor a 2000")
    @Max(value = 2100, message = "Año no puede ser mayor a 2100")
    private Integer anioEscolar;
}