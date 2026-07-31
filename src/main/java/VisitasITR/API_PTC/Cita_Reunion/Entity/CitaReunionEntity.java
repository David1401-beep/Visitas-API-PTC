package VisitasITR.API_PTC.Cita_Reunion.Entity;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CITA_REUNION")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaReunionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CITA")
    private Long idCita;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_DOCENTE", nullable = false)
    private DocenteEntity docente;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_ESTUDIANTE_ENCARGADO", nullable = false)
    private EstudianteEncargadoEntity estudianteEncargado;

    @Column(name = "CIT_MOTIVO", length = 250)
    private String motivo;

    @Column(name = "CIT_ESTADO", length = 30)
    private String estado;

    @Column(name = "CIT_OBSERVACIONES", length = 300)
    private String observaciones;

    @Column(name = "CIT_FECHA_REUNION", nullable = false)
    private LocalDateTime fechaReunion;
}
