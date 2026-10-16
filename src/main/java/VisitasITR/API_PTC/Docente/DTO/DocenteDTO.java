package VisitasITR.API_PTC.Docente.DTO;

import VisitasITR.API_PTC.Config.Patrones;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
public class DocenteDTO {

    private Long idDocente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    @Pattern(regexp = Patrones.SOLO_LETRAS, message = "El nombre solo puede llevar letras")
    private String docNombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres")
    @Pattern(regexp = Patrones.SOLO_LETRAS, message = "El apellido solo puede llevar letras")
    private String docApellido;

    // La pone la API al crear el docente (DOC001, DOC002...), no se escribe a mano.
    private String docClave;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    @Size(max = 100, message = "El correo no debe exceder los 100 caracteres")
    private String docCorreo;

    @Size(max = 100, message = "La contraseña no debe exceder los 100 caracteres")
    private String docPassword;

    @NotBlank(message = "El tipo de docente es obligatorio")
    @Size(max = 50, message = "El tipo de docente no debe exceder los 50 caracteres")
    private String docTipo;

    private String docRol;

    @Size(max = 300, message = "La URL de la foto no debe exceder los 300 caracteres")
    private String docFotoUrl;
}