package VisitasITR.API_PTC.Estudiante.Entity;

import VisitasITR.API_PTC.Usuarios.Entity.UsuariosEntity;
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

<<<<<<< HEAD
    @Column(name = "EST_ESTADO", nullable = false, length = 20)
    private String estEstado;
}
=======
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
>>>>>>> 105d2b0ff415ec3d09ebf04fcf5026e07b9d64b4
