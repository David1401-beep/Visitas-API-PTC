package VisitasITR.API_PTC.Recepcionista.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecepcionistaDTO {

    private Long idRecepcionista;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    private String recNombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres")
    private String recApellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    @Size(max = 100, message = "El correo no debe exceder los 100 caracteres")
    private String recCorreo;

    @Size(max = 100, message = "La contraseña no debe exceder los 100 caracteres")
    private String recPassword;

    private String recRol;
}