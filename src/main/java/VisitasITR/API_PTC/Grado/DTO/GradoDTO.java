package VisitasITR.API_PTC.Grado.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradoDTO {
    private Long idGrado;

    @NotBlank(message = "El nombre del grado es obligatorio")
    private String grado;

    @NotNull(message = "El ID de Nivel es obligatorio")
    private Long idNivel;
    private String nombreNivel;

    private Long idTecnica;
    private String nombreTecnica;

    private Long idEspecialidad;
    private String nombreEspecialidad;
}