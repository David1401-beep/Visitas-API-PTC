package VisitasITR.API_PTC.Materia.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDTO {
    private Long idMateria;

    @NotBlank(message = "El nombre de la materia es obligatorio")
    @Size(max = 80, message = "Máximo 80 caracteres")
    private String matNombre;

    @NotBlank(message = "El tipo de materia es obligatorio")
    @Pattern(regexp = "^(TECNICA|ACADEMICA)$", message = "Tipo de materia no válido")
    private String matTipo;
}