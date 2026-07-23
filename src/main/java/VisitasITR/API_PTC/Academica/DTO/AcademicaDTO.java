package VisitasITR.API_PTC.Academica.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcademicaDTO {

    private Long idAcademica;

    @NotBlank(message = "El nombre de la sección académica es obligatorio")
    private String seccion;
}