package VisitasITR.API_PTC.Usuarios.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Credenciales utilizadas por un encargado para entrar con el correo del estudiante.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InicioSesionEncargadoRequest {

    @NotBlank(message = "El correo del estudiante es obligatorio")
    @Email(message = "El correo del estudiante debe tener un formato válido")
    private String correoEstudiante;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
