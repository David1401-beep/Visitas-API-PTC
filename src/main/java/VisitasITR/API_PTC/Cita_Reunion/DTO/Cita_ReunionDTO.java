package VisitasITR.API_PTC.Cita_Reunion.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Cita_ReunionDTO {
    private Long idCita;

    @NotNull(message = "El ID del docente es obligatorio")
    private Long idDocente;

    @NotNull(message = "El ID del padre es obligatorio")
    private Long idPadre;

    @NotNull(message = "La fecha y hora de la reunión son obligatorias")
    private LocalDateTime citFechaReunion;

    @NotBlank(message = "El motivo de la cita es obligatorio")
    private String citMotivo;

    @NotBlank(message = "El estado de la cita es obligatorio")
    private String citEstado;

    private String citObservaciones;
}
