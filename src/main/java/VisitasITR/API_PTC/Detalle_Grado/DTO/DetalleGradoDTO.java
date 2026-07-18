package VisitasITR.API_PTC.Detalle_Grado.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleGradoDTO {

    private Long idDetalleGrado;

    @NotNull(message = "El ID de Grado es obligatorio")
    private Long idGrado;

    @NotNull(message = "El ID de Académica (Sección) es obligatorio")
    private Long idAcademica;
}
