package VisitasITR.API_PTC.Materia.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO {

    private Long idMateria;

    @NotBlank(message = "El nombre de la materia es obligatorio")
    @Size(max = 80, message = "El nombre no puede exceder los 80 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo de materia es obligatorio (ej. Técnica o Académica)")
    @Size(max = 20, message = "El tipo no puede exceder los 20 caracteres")
    private String tipo;
}