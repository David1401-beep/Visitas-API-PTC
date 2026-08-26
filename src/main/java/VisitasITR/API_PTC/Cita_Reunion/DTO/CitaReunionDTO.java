package VisitasITR.API_PTC.Cita_Reunion.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitaReunionDTO {

    private Long idCita;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;
    private String nombreDocente;

    @NotNull(message = "El ID de estudiante_encargado es obligatorio")
    private Long idEstudianteEncargado;

    @Size(max = 250, message = "Motivo excede 250 caracteres")
    private String citMotivo;

    @Pattern(regexp = "^(PENDIENTE|ACEPTADA|RECHAZADA|CANCELADA|FINALIZADA)$", message = "Estado no válido")
    private String citEstado;

    @Size(max = 300, message = "Observaciones exceden 300 caracteres")
    private String citObservaciones;

    @NotNull(message = "La fecha y hora de la reunión es obligatoria")
    private LocalDateTime citFechaReunion;
}