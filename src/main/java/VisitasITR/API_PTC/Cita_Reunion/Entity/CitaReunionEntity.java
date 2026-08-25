package VisitasITR.API_PTC.Cita_Reunion.Entity;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import VisitasITR.API_PTC.Estudiante_Encargado.Entity.EstudianteEncargadoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CITA_REUNION")
public class CitaReunionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CITA")
    private Long idCita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCENTE", nullable = false)
    private DocenteEntity docente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTUDIANTE_ENCARGADO", nullable = false)
    private EstudianteEncargadoEntity estudianteEncargado;

    @Column(name = "CIT_MOTIVO", nullable = false, length = 200)
    private String motivo;

    @Column(name = "CIT_ESTADO", nullable = false, length = 30)
    private String estado;

    @Column(name = "CIT_OBSERVACIONES", length = 300)
    private String observaciones;

    @Column(name = "CIT_FECHA_REUNION", nullable = false)
    private LocalDateTime fechaReunion;
}