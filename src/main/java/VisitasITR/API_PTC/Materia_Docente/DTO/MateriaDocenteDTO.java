package VisitasITR.API_PTC.Materia_Docente.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDocenteDTO {

    private Long idMateriaDocente;

    @NotNull(message = "El ID de la materia es obligatorio")
    private Long idMateria;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;
}