package VisitasITR.API_PTC.Recuperacion.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambiarPasswordDTO {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "[0-9]{6}", message = "El código son 6 dígitos")
    private String codigo;

    // La misma regla va aqui y en el formulario. Si solo estuviera en el
    // formulario, cualquiera podria saltarsela llamando a la API directo.
    @NotBlank(message = "La contraseña nueva es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String passwordNueva;
}
