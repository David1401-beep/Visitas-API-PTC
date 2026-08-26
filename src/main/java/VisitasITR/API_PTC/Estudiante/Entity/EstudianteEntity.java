package VisitasITR.API_PTC.Estudiante.Entity;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTUDIANTE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTUDIANTE")
    private Long idEstudiante;

    @Column(name = "EST_NOMBRE", nullable = false, length = 60)
    private String estNombre;

    @Column(name = "EST_APELLIDO", nullable = false, length = 60)
    private String estApellido;

    @Column(name = "EST_CORREO", nullable = false, unique = true, length = 100)
    private String estCorreo;

    @Column(name = "EST_PASSWORD", nullable = false, length = 100)
    private String estPassword;

    @Column(name = "EST_GRADO", nullable = false, length = 40)
    private String estGrado;

    @Column(name = "EST_SECCION", length = 20)
    private String estSeccion;

    @Column(name = "EST_ESPECIALIDAD", length = 60)
    private String estEspecialidad;

    @Column(name = "EST_CODIGO", nullable = false, unique = true, length = 20)
    private String estCodigo;

    @Column(name = "EST_ROL", nullable = false, length = 25)
    private String estRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACADEMICA", nullable = false)
    private AcademicaEntity academica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRADO", nullable = false)
    private GradoEntity grado;
}