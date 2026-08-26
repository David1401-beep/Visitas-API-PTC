package VisitasITR.API_PTC.Estudiante_Encargado.Entity;

import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESTUDIANTE_ENCARGADO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEncargadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTUDIANTE_ENCARGADO")
    private Long idEstudianteEncargado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTUDIANTE", nullable = false)
    private EstudianteEntity estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENCARGADO", nullable = false)
    private EncargadoEntity encargado;
}