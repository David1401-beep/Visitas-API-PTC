package VisitasITR.API_PTC.Estudiante.Entity;

import VisitasITR.API_PTC.Usuarios.Entity.UsuariosEntity;
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
    private String estNombre;

    @Column(name = "EST_APELLIDO", nullable = false, length = 60)
    private String estApellido;

    @Column(name = "EST_GRADO", nullable = false, length = 40)
    private String estGrado;

    @Column(name = "EST_SECCION", nullable = false, length = 20)
    private String estSeccion;

    @Column(name = "EST_ESPECIALIDAD", nullable = false, length = 60)
    private String estEspecialidad;

    @Column(name = "EST_CODIGO", nullable = false, length = 20, unique = true)
    private String estCodigo;

    @Column(name = "ID_ACADEMICA", nullable = false)
    private Long idAcademica;

    @Column(name = "ID_GRADO", nullable = false)
    private Long idGrado;

    /**
     * Usuario que contiene el correo institucional del estudiante y que utiliza
     * el encargado para iniciar sesión en la aplicación de padres.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ESTUDIANTE", nullable = false)
    private UsuariosEntity usuarioEstudiante;
}
