package VisitasITR.API_PTC.Docente.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DocenteDTO {

    private Long id;

    @NotBlank(message = "El nombre del docente es obligatorio")
    private String docNombre;

    @NotBlank(message = "El apellido del docente es obligatorio")
    private String docApellido;

    @NotBlank(message = "El correo del docente es obligatorio")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    private String docCorreo;

    @NotBlank(message = "El rol del docente es obligatorio")
    private String docRol;

}
