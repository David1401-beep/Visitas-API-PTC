package VisitasITR.API_PTC.Estudiante_Encargado.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteEncargadoDTO {
    private Long idEstudianteEncargado;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;
    private String nombreEstudiante;

    @NotNull(message = "El ID del encargado es obligatorio")
    private Long idEncargado;
    private String nombreEncargado;
}