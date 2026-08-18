package VisitasITR.API_PTC.Encargado.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EncargadoDTO {

    private Long idEncargado;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    private String apellido;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}$", message = "El teléfono debe cumplir el formato ####-####")
    private String telefono;

    @NotBlank(message = "El tipo de encargado es obligatorio")
    private String tipo;
}