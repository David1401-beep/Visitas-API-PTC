package VisitasITR.API_PTC.Materia_Docente.Entity;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "MATERIA_DOCENTE",
        uniqueConstraints = @UniqueConstraint(name = "MATERIA_DOCENTE_DOCENTE_UQ", columnNames = "ID_DOCENTE")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDocenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MATERIA_DOCENTE")
    private Long idMateriaDocente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MATERIA", nullable = false)
    private MateriaEntity materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCENTE", nullable = false, unique = true)
    private DocenteEntity docente;
}