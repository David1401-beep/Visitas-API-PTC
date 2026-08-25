package VisitasITR.API_PTC.Estudiante.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ESTUDIANTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTUDIANTE")
    private Long idEstudiante;

    @Column(name = "EST_NOMBRES", nullable = false, length = 100)
    private String estNombres;

    @Column(name = "EST_APELLIDOS", nullable = false, length = 100)
    private String estApellidos;

    @Column(name = "EST_NIE", nullable = false, unique = true, length = 20)
    private String estNie;

    @Column(name = "EST_CORREO", unique = true, length = 150)
    private String estCorreo;

    @Column(name = "EST_GRADO", nullable = false, length = 50)
    private String estGrado;

    @Column(name = "EST_SECCION", nullable = false, length = 10)
    private String estSeccion;

    @Column(name = "EST_ESTADO", nullable = false, length = 20)
    private String estEstado;
}