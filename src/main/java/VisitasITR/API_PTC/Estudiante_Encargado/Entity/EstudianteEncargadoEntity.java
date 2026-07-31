package VisitasITR.API_PTC.Estudiante_Encargado.Entity;

import VisitasITR.API_PTC.Encargado.Entity.EncargadoEntity;
import VisitasITR.API_PTC.Estudiante.Entity.EstudianteEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "ESTUDIANTE_ENCARGADO",
        uniqueConstraints = @UniqueConstraint(
                name = "ESTUDIANTE_ENCARGADO_UQ",
                columnNames = {"ID_ESTUDIANTE", "ID_PADRE"}
        )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEncargadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTUDIANTE_ENCARGADO")
    private Long idEstudianteEncargado;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_ESTUDIANTE", nullable = false)
    private EstudianteEntity estudiante;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_PADRE", nullable = false)
    private EncargadoEntity encargado;

    @Column(name = "PARENTESCO", nullable = false, length = 30)
    private String parentesco;
}
