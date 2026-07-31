package VisitasITR.API_PTC.Docente_Grado.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 2000, message = "El año escolar no puede ser menor que 2000")
    @Max(value = 2100, message = "El año escolar no puede ser mayor que 2100")
    private Integer anioEscolar;
}

