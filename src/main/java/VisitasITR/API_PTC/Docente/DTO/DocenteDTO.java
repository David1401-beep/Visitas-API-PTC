package VisitasITR.API_PTC.Docente.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocenteDTO {

    private Long idDocente;

    @NotBlank(message = "El nombre del docente es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido del docente es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellido;

    @NotBlank(message = "La clave del docente es obligatoria")
    @Size(max = 10, message = "La clave no puede superar los 10 caracteres")
    private String clave;

    @NotBlank(message = "El correo del docente es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    private String correo;

    @NotBlank(message = "El rol del docente es obligatorio")
    @Size(max = 50, message = "El rol no puede superar los 50 caracteres")
    private String rol;

    @Size(max = 20, message = "El tipo de docente no puede superar los 20 caracteres")
    private String tipo;
}
