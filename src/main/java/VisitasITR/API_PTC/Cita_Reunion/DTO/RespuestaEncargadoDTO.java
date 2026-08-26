package VisitasITR.API_PTC.Cita_Reunion.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Contiene únicamente los datos que el encargado puede modificar al responder
 * una convocatoria. Así se evita que mobile sobrescriba el docente, estudiante
 * o motivo original de la cita.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaEncargadoDTO {

    @NotNull(message = "El ID de la relación estudiante-encargado es obligatorio")
    private Long idEstudianteEncargado;

    @NotBlank(message = "El estado de la respuesta es obligatorio")
    private String estado;

    private LocalDateTime nuevaFechaReunion;

    @Size(max = 250, message = "El motivo de reprogramación no puede superar los 250 caracteres")
    private String motivoReprogramacion;
}
