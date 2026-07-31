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

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    @Size(max = 60, message = "El nombre no puede exceder los 60 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    @Size(max = 60, message = "El apellido no puede exceder los 60 caracteres")
    private String apellido;

    @Size(max = 40, message = "El grado no puede exceder los 40 caracteres")
    private String grado;

    @Size(max = 20, message = "La sección no puede exceder los 20 caracteres")
    private String seccion;

    @Size(max = 60, message = "La especialidad no puede exceder los 60 caracteres")
    private String especialidad;

    @NotBlank(message = "El código del estudiante es obligatorio")
    @Size(max = 20, message = "El código no puede exceder los 20 caracteres")
    private String codigo;

    @NotNull(message = "El ID de la sección académica es obligatorio")
    private Long idAcademica;

    @NotNull(message = "El ID del grado es obligatorio")
    private Long idGrado;
}
