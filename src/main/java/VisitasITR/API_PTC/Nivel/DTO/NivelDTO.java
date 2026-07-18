package VisitasITR.API_PTC.Nivel.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NivelDTO {

    private Long idNivel;

    @NotBlank(message = "El nombre del nivel es obligatorio")
    @Size(max = 20, message = "El nombre del nivel no puede superar los 20 caracteres")
    private String nivel;
}