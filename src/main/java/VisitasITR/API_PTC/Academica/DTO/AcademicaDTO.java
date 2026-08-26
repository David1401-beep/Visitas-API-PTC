package VisitasITR.API_PTC.Academica.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicaDTO {
    private Long idAcademica;

    @NotBlank(message = "El nombre de académica es obligatorio")
    @Pattern(regexp = "^(A1|A2|A3|A4|A5|B1|B2|B3|B4|A|B|C|D|E|F)$", message = "Valor académico no válido")
    private String academica;
}