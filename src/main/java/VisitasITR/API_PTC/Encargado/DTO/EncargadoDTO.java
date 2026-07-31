package VisitasITR.API_PTC.Encargado.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncargadoDTO {

    private Long idPadre;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long idUsuario;

    @NotBlank(message = "El nombre del encargado es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del encargado es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    private String apellido;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    @Pattern(regexp = "^$|^[0-9]{4}-[0-9]{4}$", message = "El teléfono debe tener el formato 0000-0000")
    private String telefono;
}
