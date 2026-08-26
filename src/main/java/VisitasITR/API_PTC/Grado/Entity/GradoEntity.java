package VisitasITR.API_PTC.Grado.Entity;

import VisitasITR.API_PTC.Especialidad.Entity.EspecialidadEntity;
import VisitasITR.API_PTC.Nivel.Entity.NivelEntity;
import VisitasITR.API_PTC.Seccion_Tecnica.Entity.SeccionTecnicaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GRADO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRADO")
    private Long idGrado;

    @Column(name = "GRADO", nullable = false, length = 40)
    private String grado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NIVEL", nullable = false)
    private NivelEntity nivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TECNICA")
    private SeccionTecnicaEntity seccionTecnica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESPECIALIDAD")
    private EspecialidadEntity especialidad;
}