package VisitasITR.API_PTC.Cita_Reunion.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitaReunionDTO {

    private Long idCita;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;

    @NotNull(message = "El ID del estudiante-encargado es obligatorio")
    private Long idEstudianteEncargado;

    // Datos informativos de respuesta. No se reciben para crear o actualizar.
    private Long idEstudiante;
    private String nombreEstudiante;
    private String nombreEncargado;
    private String nombreEmpleado;

    @NotBlank(message = "El motivo de la cita es obligatorio")
    @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
    private String motivo;

    @NotBlank(message = "El estado de la cita es obligatorio")
<<<<<<< HEAD
    @Pattern(
            regexp = "PENDIENTE|ACEPTADA|RECHAZADA|CANCELADA|FINALIZADA",
            message = "El estado de la cita debe ser: PENDIENTE, ACEPTADA, RECHAZADA, CANCELADA o FINALIZADA"
    )
=======
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
    private String estado;

    @Size(max = 300, message = "Las observaciones no pueden superar los 300 caracteres")
    private String observaciones;

    @NotNull(message = "La fecha y hora de la reunión es obligatoria")
    @Future(message = "La fecha y la hora tiene que ser futura")
    private LocalDateTime fechaReunion;
}
