package VisitasITR.API_PTC.Grupo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GrupoDTO {

    private Long idGrupo;

    @NotNull
    private Integer grupo;

    @NotNull
    private Long idNivel;

    @NotNull
    private Long idEspecialidad;

    @NotNull
    private Long idAcademica;

    @NotNull
    private Long idTecnica;
}
