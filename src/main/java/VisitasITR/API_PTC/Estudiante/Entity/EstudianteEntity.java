package VisitasITR.API_PTC.Estudiante.Entity;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;
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

    @Column(name = "EST_NIE", nullable = false, length = 15)
    private String nie;

    @Column(name = "EST_NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "EST_APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "EST_GRADO", length = 30)
    private String grado;

    @Column(name = "EST_SECCION", length = 10)
    private String seccion;

    @Column(name = "EST_ESPECIALIDAD", length = 60)
    private String especialidad;

    @Column(name = "EST_CODIGO", length = 20)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACADEMICA", nullable = false)
    private AcademicaEntity academica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DETALLE_GRADO", nullable = false)
    private DetalleGradoEntity detalleGrado;
}