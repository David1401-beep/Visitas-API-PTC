package VisitasITR.API_PTC.Docente.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DocenteDTO {

    private Long id;

    @NotBlank(message = "ERRO1: El nombre del docente es obligatorio")
    private String docNombre;

    @NotBlank(message = "ERRO2: El apellido del docente es obligatorio")
    private String docApellido;

    @NotBlank(message = "ERRO3: El correo del docente es obligatorio")
    @Email(message = "ERRO4: Debe ser un formato de correo electrónico válido")
    private String docCorreo;

    @NotBlank(message = "ERRO5: El rol del docente es obligatorio")
    private String docRol;

}
