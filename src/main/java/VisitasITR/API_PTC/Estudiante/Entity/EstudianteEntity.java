package VisitasITR.API_PTC.Estudiante.Entity;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "ESTUDIANTE",
        uniqueConstraints = @UniqueConstraint(name = "ESTUDIANTE_CODIGO_UQ", columnNames = "EST_CODIGO")
)
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
    private String nombre;

    @Column(name = "EST_APELLIDO", nullable = false, length = 60)
    private String apellido;

    @Column(name = "EST_GRADO", length = 40)
    private String grado;

    @Column(name = "EST_SECCION", length = 20)
    private String seccion;

    @Column(name = "EST_ESPECIALIDAD", length = 60)
    private String especialidad;

    @Column(name = "EST_CODIGO", nullable = false, unique = true, length = 20)
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_ACADEMICA", nullable = false)
    private AcademicaEntity academica;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_GRADO", nullable = false)
    private GradoEntity gradoRelacionado;
}
