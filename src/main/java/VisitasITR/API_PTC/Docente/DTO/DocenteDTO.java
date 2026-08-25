package VisitasITR.API_PTC.Docente.DTO;

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
public class DocenteDTO {

    private Long idDocente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    private String docNombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres")
    private String docApellido;

    @NotBlank(message = "La clave es obligatoria")
    @Size(max = 20, message = "La clave no debe exceder los 20 caracteres")
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
}