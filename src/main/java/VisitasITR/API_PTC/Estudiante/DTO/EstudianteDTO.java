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
    @Size(max = 60, message = "El nombre no puede superar los 60 caracteres")
    private String estNombre;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    @Size(max = 60, message = "El apellido no puede superar los 60 caracteres")
    private String estApellido;

    @NotBlank(message = "El grado es obligatorio")
    @Size(max = 40, message = "El grado no puede superar los 40 caracteres")
    private String estGrado;

    @NotBlank(message = "La sección es obligatoria")
    @Size(max = 20, message = "La sección no puede superar los 20 caracteres")
    private String estSeccion;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 60, message = "La especialidad no puede superar los 60 caracteres")
    private String estEspecialidad;

    @NotBlank(message = "El código del estudiante es obligatorio")
    @Size(max = 20, message = "El código no puede superar los 20 caracteres")
    private String estCodigo;

    @NotNull(message = "El ID de la sección académica es obligatorio")
    private Long idAcademica;

    @NotNull(message = "El ID del grado es obligatorio")
    private Long idGrado;

    @NotNull(message = "El ID del usuario asociado es obligatorio")
    private Long usuarioEstudiante;
}