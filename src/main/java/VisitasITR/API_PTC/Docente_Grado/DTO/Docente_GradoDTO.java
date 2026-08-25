package VisitasITR.API_PTC.Docente_Grado.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Docente_GradoDTO {

    private Long idDocenteGrado;

    @NotNull(message = "El ID del docente (empleado) es obligatorio")
    private Long idDocente;

    @NotNull(message = "El ID del grado es obligatorio")
    private Long idGrado;

    @NotNull(message = "El año escolar es obligatorio")
    private Integer anioEscolar;

    private String nombreDocente;
    private String nombreGrado;
}