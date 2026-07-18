package VisitasITR.API_PTC.Academica.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicaDTO {

    private Long idAcademica;

    @NotBlank(message = "El nombre de la sección académica es obligatorio")
    @Size(max = 2, message = "La sección no puede superar los 2 caracteres")
    private String seccion;
}
