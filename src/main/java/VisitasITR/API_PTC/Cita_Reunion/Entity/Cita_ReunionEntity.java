package VisitasITR.API_PTC.Cita_Reunion.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CITA_REUNION")
@Getter
@Setter
public class Cita_ReunionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;


    @Column(name = "id_docente", nullable = false)
    private Long idDocente;

    @Column(name = "id_padre", nullable = false)
    private Long idPadre;

    @Column(name = "CIT_fecha_reunion")
    private LocalDateTime citFechaReunion;

    @Column(name = "CIT_motivo")
    private String citMotivo;

    @Column(name = "CIT_estado")
    private String citEstado;

    @Column(name = "CIT_observaciones")
    private String citObservaciones;
}
