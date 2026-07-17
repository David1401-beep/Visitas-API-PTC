package VisitasITR.API_PTC.Estudiante.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ESTUDIANTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTUDIANTE")
    private Integer idEstudiante;
    @Column(name = "EST_NOMBRE")
    private String estNombre;
    @Column(name = "EST_APELLIDO")
    private String estApellido;
    @Column(name = "EST_GRADO")
    private String estGrado;
    @Column(name = "EST_SECCION")
    private String estSeccion;
    @Column(name = "EST_ESPECIALIDAD")
    private String estEspecialidad;
    @Column(name = "EST_CODIGO")
    private String estCodigo;
    @Column(name = "ID_PADRE")
    private Integer idPadre;
    @Column(name = "ID_DETALLESGRADO")
    private Integer idDetallesGrado;
}

