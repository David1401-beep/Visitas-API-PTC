package VisitasITR.API_PTC.Materia_Docente.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDocenteDTO {
    private Long idMateriaDocente;

    @NotNull(message = "El ID de materia es obligatorio")
    private Long idMateria;
    private String nombreMateria;

    @NotNull(message = "El ID de docente es obligatorio")
    private Long idDocente;
    private String nombreDocente;
}