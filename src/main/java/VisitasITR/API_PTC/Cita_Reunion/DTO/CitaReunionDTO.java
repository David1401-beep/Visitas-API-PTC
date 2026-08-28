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

    private String nombreEstudiante;
    private String nombreEncargado;

    @Size(max = 250, message = "El motivo no puede exceder 250 caracteres")
    private String citMotivo;

    @Pattern(
            regexp = "^(PENDIENTE|ACEPTADA|RECHAZADA|CANCELADA|FINALIZADA|POSPUESTA)$",
            message = "Estado no valido. Use PENDIENTE, ACEPTADA, RECHAZADA, CANCELADA, FINALIZADA o POSPUESTA"
    )
    private String citEstado;

    @Size(max = 300, message = "Las observaciones no pueden exceder 300 caracteres")
    private String citObservaciones;

    @NotNull(message = "La fecha y hora de la reunion es obligatoria")
    private LocalDateTime citFechaReunion;
}