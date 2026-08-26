package VisitasITR.API_PTC.Nivel.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NivelDTO {
    private Long idNivel;

    @NotBlank(message = "El nivel es obligatorio")
    @Size(max = 40, message = "Máximo 40 caracteres")
    private String nivel;
}