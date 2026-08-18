package VisitasITR.API_PTC.Materia.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDTO {

    private Long idMateria;

    @NotBlank(message = "El nombre de la materia es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo de materia es obligatorio")
    private String tipo;
}