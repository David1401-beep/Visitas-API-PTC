package VisitasITR.API_PTC.Estudiante.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {

    private Long idEstudiante;

    @NotBlank(message = "El NIE del estudiante es obligatorio")
    @Size(max = 15, message = "El NIE no puede exceder los 15 caracteres")
    private String nie;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    private String apellido;

    @NotNull(message = "El ID de Detalle Grado es obligatorio")
    private Long idDetalleGrado;
}