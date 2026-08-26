package VisitasITR.API_PTC.Encargado.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EncargadoDTO {
    private Long idEncargado;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String encNombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String encApellido;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}$", message = "El teléfono debe cumplir el formato 0000-0000")
    private String encTelefono;

    @NotBlank(message = "El tipo de encargado es obligatorio")
    @Pattern(regexp = "^(PADRE|MADRE|HERMANO MAYOR|HERMANA MAYOR|TIO|TIA|ABUELO|ABUELA|TUTOR LEGAL)$", message = "Tipo no permitido")
    private String encTipo;
}