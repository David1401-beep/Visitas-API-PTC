package VisitasITR.API_PTC.Comunicado.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComunicadoDTO {

    private Long idComunicado;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;

    private String nombreDocente;

    @NotBlank(message = "El mensaje del comunicado es obligatorio")
    @Size(max = 500, message = "El mensaje no puede exceder 500 caracteres")
    private String comMensaje;

    private LocalDateTime comFecha;

    private String comActivo;
}