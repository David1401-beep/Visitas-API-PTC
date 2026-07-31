package VisitasITR.API_PTC.Academica.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AcademicaDTO {

    private Long idAcademica;

    @NotBlank(message = "El nombre de la sección académica es obligatorio")
    @Size(max = 60, message = "La sección académica no puede superar los 60 caracteres")
    @Pattern(
            regexp = "A1|A2|A3|A4|A5|B1|B2|B3|B4|A|B|C|D|E|F",
            message = "La sección académica enviada no está permitida"
    )
    private String seccion;
}
