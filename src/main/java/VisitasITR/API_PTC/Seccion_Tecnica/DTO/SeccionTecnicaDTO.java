package VisitasITR.API_PTC.Seccion_Tecnica.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeccionTecnicaDTO {
    private Long idTecnica;

    @NotBlank(message = "La sección técnica es obligatoria")
    @Pattern(regexp = "^(1A|2A|1B|2B|1C|2C|3C)$", message = "Sección técnica no válida")
    private String tecnica;
}