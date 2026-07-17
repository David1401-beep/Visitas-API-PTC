package VisitasITR.API_PTC.Estudiante.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteDTO {

    private Integer idEstudiante;
    private String estNombre;
    private String estApellido;
    private String estGrado;
    private String estSeccion;
    private String estEspecialidad;
    private String estCodigo;
    private Integer idPadre;
    private Integer idDetallesGrado;
}

