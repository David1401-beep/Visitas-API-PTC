package VisitasITR.API_PTC.Recuperacion.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificarCodigoDTO {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "[0-9]{6}", message = "El código son 6 dígitos")
    private String codigo;
}
