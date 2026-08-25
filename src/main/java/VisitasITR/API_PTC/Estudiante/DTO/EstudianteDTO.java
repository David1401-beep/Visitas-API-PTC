package VisitasITR.API_PTC.Estudiante.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EstudianteDTO {
    private Long idEstudiante;

    @NotBlank(message = "Los nombres son obligatorios")
    private String estNombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String estApellidos;

    @NotBlank(message = "El NIE es obligatorio")
    private String estNie;

    private String estCorreo;

    @NotBlank(message = "El grado es obligatorio")
    private String estGrado;

    @NotBlank(message = "La sección es obligatoria")
    private String estSeccion;

    private String estEstado;
}