package VisitasITR.API_PTC.Detalle_Grado.Entity;

import VisitasITR.API_PTC.Academica.Entity.AcademicaEntity;
import VisitasITR.API_PTC.Grado.Entity.GradoEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DETALLE_GRADO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleGradoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_GRADO")
    private Long idDetalleGrado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRADO", nullable = false)
    private GradoEntity grado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACADEMICA", referencedColumnName = "ID_ACADEMICA")
    private AcademicaEntity academica;
}
