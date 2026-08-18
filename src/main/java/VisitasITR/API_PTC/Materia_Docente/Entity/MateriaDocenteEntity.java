package VisitasITR.API_PTC.Materia_Docente.Entity;

import VisitasITR.API_PTC.Empleado.Entity.EmpleadoEntity;
import VisitasITR.API_PTC.Materia.Entity.MateriaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MATERIA_DOCENTE")
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
    @JoinColumn(name = "ID_EMPLEADO", nullable = false, unique = true)
    private EmpleadoEntity empleado;
}