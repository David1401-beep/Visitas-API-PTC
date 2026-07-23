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

    @Size(max = 30, message = "El grado no puede exceder los 30 caracteres")
    private String grado;

    @Size(max = 10, message = "La sección no puede exceder los 10 caracteres")
    private String seccion;

    @Size(max = 60, message = "La especialidad no puede exceder los 60 caracteres")
    private String especialidad;

    @Size(max = 20, message = "El código no puede exceder los 20 caracteres")
    private String codigo;

    @NotNull(message = "El ID de Académica es obligatorio")
    private Long idAcademica;

    @NotNull(message = "El ID de Detalle Grado es obligatorio")
    private Long idDetalleGrado;
}