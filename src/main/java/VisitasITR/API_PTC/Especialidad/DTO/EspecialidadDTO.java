package VisitasITR.API_PTC.Especialidad.DTO;

import VisitasITR.API_PTC.Config.Patrones;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = Patrones.SOLO_LETRAS, message = "El nombre de la especialidad solo puede llevar letras")
    private String especialidad;
}