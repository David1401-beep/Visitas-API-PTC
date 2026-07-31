package VisitasITR.API_PTC.Estudiante_Encargado.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEncargadoDTO {

    private Long idEstudianteEncargado;

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;

    @NotNull(message = "El ID del encargado es obligatorio")
    private Long idPadre;

    @NotBlank(message = "El parentesco es obligatorio")
    @Size(max = 30, message = "El parentesco no puede exceder los 30 caracteres")
    @Pattern(
            regexp = "PADRE|MADRE|HERMANO MAYOR|HERMANA MAYOR|TIO|TIA|ABUELO|ABUELA",
            message = "El parentesco enviado no está permitido"
    )
    private String parentesco;
}
