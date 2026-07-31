package VisitasITR.API_PTC.Materia_Docente.Entity;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MATERIA_DOCENTE", uniqueConstraints = @UniqueConstraint(name = "MAT_DOC_DOCENTE_UQ", columnNames = "ID_DOCENTE"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDocenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MATERIA_DOCENTE")
    private Long idMateriaDocente;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_MATERIA", nullable = false)
    private MateriaEntity materia;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_DOCENTE", nullable = false, unique = true)
    private DocenteEntity docente;
}
