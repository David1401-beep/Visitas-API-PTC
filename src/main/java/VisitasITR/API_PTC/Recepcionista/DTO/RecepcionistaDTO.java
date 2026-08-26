package VisitasITR.API_PTC.Recepcionista.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecepcionistaDTO {

    private Long idRecepcionista;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String recNombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String recApellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String recCorreo;

    @Size(max = 100, message = "Máximo 100 caracteres")
    private String recPassword;

    private String recRol;
}