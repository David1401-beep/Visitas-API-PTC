package VisitasITR.API_PTC.Seccion_Tecnica.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeccionTecnicaDTO {

    private Long idTecnica;

    @NotBlank(message = "El nombre de la sección técnica es obligatorio")
    @Size(max = 20, message = "El nombre técnico no puede superar los 20 caracteres")
    @Pattern(
            regexp = "A1|A2|B1|B2|C1|C2",
            message = "La sección técnica enviada no está permitida"
    )
    private String tecnica;
}