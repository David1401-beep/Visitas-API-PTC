package VisitasITR.API_PTC.Grado.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradoDTO {

    private Long idGrado;

    @NotBlank(message = "El nombre del grado es obligatorio")
    @Size(max = 20, message = "El grado no puede superar los 20 caracteres")
    private String grado;

    @NotNull(message = "El ID de Nivel es obligatorio")
    private Long idNivel;

    private Long idTecnica;

    private Long idEspecialidad;
}