package VisitasITR.API_PTC.Seccion_Tecnica.DTO;

import jakarta.validation.constraints.NotBlank;
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
    private String tecnica;
}