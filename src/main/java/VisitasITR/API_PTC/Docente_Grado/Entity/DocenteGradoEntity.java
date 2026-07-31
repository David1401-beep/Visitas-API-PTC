package VisitasITR.API_PTC.Docente_Grado.Entity;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "Docente_Grado",
        uniqueConstraints = @UniqueConstraint(
                name = "DOCENTE_GRADO_UQ",
                columnNames = {"ID_DOCENTE", "ID_GRADO", "ANIO_ESCOLAR"}
        )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocenteGradoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOCENTE_GRADO")
    private Long idDocenteGrado;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_DOCENTE", nullable = false)
    private DocenteEntity docente;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_GRADO", nullable = false)
    private GradoEntity grado;

    @Column(name = "ANIO_ESCOLAR", nullable = false)
    private Integer anioEscolar;
}

