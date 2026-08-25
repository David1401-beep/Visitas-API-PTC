package VisitasITR.API_PTC.Especialidad.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadDTO {

    private Long idEspecialidad;

    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    @Size(max = 60, message = "La especialidad no puede superar los 60 caracteres")
    private String especialidad;
}