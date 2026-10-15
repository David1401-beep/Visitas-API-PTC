package VisitasITR.API_PTC.Estudiante.DTO;

import VisitasITR.API_PTC.Config.Patrones;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {

    private Long idEstudiante;

    @NotBlank(message = "Nombre obligatorio")
    @Pattern(regexp = Patrones.SOLO_LETRAS, message = "El nombre solo puede llevar letras")
    private String estNombre;

    @NotBlank(message = "Apellido obligatorio")
    @Pattern(regexp = Patrones.SOLO_LETRAS, message = "El apellido solo puede llevar letras")
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