package VisitasITR.API_PTC.Nivel.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NivelDTO {

    private Long idNivel;

    @NotNull
    private Integer nivel;
}
