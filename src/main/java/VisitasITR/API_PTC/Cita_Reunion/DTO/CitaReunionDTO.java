package VisitasITR.API_PTC.Cita_Reunion.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaReunionDTO {

    private Long idCita;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;

    @NotNull(message = "El ID de la relación estudiante-encargado es obligatorio")
    private Long idEstudianteEncargado;

    @Size(max = 250, message = "El motivo no puede exceder los 250 caracteres")
    private String motivo;

    @Size(max = 30, message = "El estado no puede exceder los 30 caracteres")
    @Pattern(
            regexp = "PENDIENTE|ACEPTADA|RECHAZADA|CANCELADA|FINALIZADA",
            message = "El estado de la cita no está permitido"
    )
    private String estado;

    @Size(max = 300, message = "Las observaciones no pueden exceder los 300 caracteres")
    private String observaciones;

    @NotNull(message = "La fecha de la reunión es obligatoria")
    private LocalDateTime fechaReunion;
}
