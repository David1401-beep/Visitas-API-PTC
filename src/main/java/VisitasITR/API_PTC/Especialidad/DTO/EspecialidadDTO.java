package VisitasITR.API_PTC.Especialidad.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadDTO {
    private Long idEspecialidad;

    @NotBlank(message = "Nombre de especialidad obligatorio")
    @Size(max = 60, message = "Máximo 60 caracteres")
    private String especialidad;
}