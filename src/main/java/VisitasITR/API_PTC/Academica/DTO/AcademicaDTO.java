package VisitasITR.API_PTC.Academica.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AcademicaDTO {

    private Long idAcademica;

    @NotBlank(message = "El campo Academica es obligatorio")
    private String academica;

}
