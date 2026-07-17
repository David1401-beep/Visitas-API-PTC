package VisitasITR.API_PTC.Detalles_Grado.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Detalles_GradoDTO {

    private Long id;

    @NotNull (message = "El número de detalle grado es obligatorio")
    private Integer detalleGrado;

    @NotNull (message = "El ID del grupo es obligatorio")
    private  Long idGrupo;
}
