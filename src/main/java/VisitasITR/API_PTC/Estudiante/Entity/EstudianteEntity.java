package VisitasITR.API_PTC.Estudiante.Entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DETALLE_GRADO", nullable = false)
    private DetalleGradoEntity detalleGrado;
}