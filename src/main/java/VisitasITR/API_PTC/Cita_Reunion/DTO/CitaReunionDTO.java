package VisitasITR.API_PTC.Cita_Reunion.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long idEmpleado;

    @NotNull(message = "El ID del estudiante-encargado es obligatorio")
    private Long idEstudianteEncargado;

    @NotBlank(message = "El motivo de la cita es obligatorio")
    @Size(max = 200, message = "El motivo no puede superar los 200 caracteres")
    private String motivo;

    @NotBlank(message = "El estado de la cita es obligatorio")
    @Size(max = 20, message = "El estado no puede superar los 20 caracteres")
    private String estado;

    @Size(max = 255, message = "Las observaciones no pueden superar los 255 caracteres")
    private String observaciones;

    @NotNull(message = "La fecha y hora de la reunión es obligatoria")
    private LocalDateTime fechaReunion;
}