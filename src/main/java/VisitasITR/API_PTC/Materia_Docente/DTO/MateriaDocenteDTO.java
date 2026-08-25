package VisitasITR.API_PTC.Materia_Docente.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDocenteDTO {

    private Long idMateriaDocente;

    @NotNull(message = "El ID de la materia es obligatorio")
    private Long idMateria;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;

    private String nombreMateria;
    private String nombreDocente;
}