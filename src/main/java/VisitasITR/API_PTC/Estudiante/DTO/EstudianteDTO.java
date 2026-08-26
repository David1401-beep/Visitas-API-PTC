package VisitasITR.API_PTC.Estudiante.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {

    private Long idEstudiante;

    @NotBlank(message = "Nombre obligatorio")
    private String estNombre;

    @NotBlank(message = "Apellido obligatorio")
    private String estApellido;

    @NotBlank(message = "Correo obligatorio")
    @Email(message = "Correo inválido")
    private String estCorreo;

    private String estPassword;

    @NotBlank(message = "Grado obligatorio")
    private String estGrado;

    private String estSeccion;
    private String estEspecialidad;

    @NotBlank(message = "Código obligatorio")
    private String estCodigo;

    private String estRol;

    @NotNull(message = "ID de Académica obligatorio")
    private Long idAcademica;
    private String nombreAcademica;

    @NotNull(message = "ID de Grado obligatorio")
    private Long idGrado;
    private String nombreGrado;
}