package VisitasITR.API_PTC.Usuarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Datos mínimos de la sesión. La contraseña nunca debe regresar al frontend.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesionEncargadoDTO {

    private Long idUsuario;
    private String correoEstudiante;
    private String rol;
    private List<Long> idsEstudiante;
    private List<Long> idsEstudianteEncargado;
}
