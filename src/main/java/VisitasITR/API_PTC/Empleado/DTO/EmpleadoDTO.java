package VisitasITR.API_PTC.Empleado.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

    private Long idEmpleado;

    @NotBlank(message = "El nombre del empleado es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String empNombre;

    @NotBlank(message = "El apellido del empleado es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String empApellido;

    @NotBlank(message = "La clave es obligatoria")
    @Size(max = 20, message = "La clave no puede superar los 20 caracteres")
    private String empClave;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe proporcionar un formato de correo válido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    private String empCorreo;

    @NotBlank(message = "El rol del empleado es obligatorio")
    @Size(max = 50, message = "El rol no puede superar los 50 caracteres")
    private String empRol;

    @NotNull(message = "El ID del usuario asociado es obligatorio")
    private Long usuarioEmpleado;
}