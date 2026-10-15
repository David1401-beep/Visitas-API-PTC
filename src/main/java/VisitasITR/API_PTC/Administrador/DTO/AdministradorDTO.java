package VisitasITR.API_PTC.Administrador.DTO;

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
public class AdministradorDTO {

    private Long idAdministrador;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe exceder los 50 caracteres")
    @Pattern(regexp = Patrones.SOLO_LETRAS, message = "El nombre solo puede llevar letras")
    private String admNombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres")
    @Pattern(regexp = Patrones.SOLO_LETRAS, message = "El apellido solo puede llevar letras")
    private String admApellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    @Size(max = 100, message = "El correo no debe exceder los 100 caracteres")
    private String admCorreo;

    @Size(max = 100, message = "La contraseña no debe exceder los 100 caracteres")
    private String admPassword;

    private String admRol;
}