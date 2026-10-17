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
    // Antes era una lista fija. Ahora se pueden agregar secciones nuevas con
    // el mismo formato: una letra (tercer ciclo) o letra y numero (bachillerato).
    @Pattern(regexp = "^\\s*[A-Za-z][0-9]?\\s*$", message = "La sección debe ser una letra o una letra con un número (ej. G o B5)")
    private String academica;
}