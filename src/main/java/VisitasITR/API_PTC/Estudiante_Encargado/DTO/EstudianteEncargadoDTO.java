package VisitasITR.API_PTC.EstudianteEncargado.DTO;

import VisitasITR.API_PTC.Estudiante.DTO.EstudianteDTO;
import VisitasITR.API_PTC.Encargado.DTO.EncargadoDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteEncargadoDTO {

    private Long idEstudianteEncargado;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;

    @NotNull(message = "El ID del encargado es obligatorio")
    private Long idEncargado;

    private String nombreEstudiante;
    private String nombreEncargado;
}