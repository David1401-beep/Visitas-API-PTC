package VisitasITR.API_PTC.Comunicado.Entity;

import VisitasITR.API_PTC.Docente.Entity.DocenteEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMUNICADO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComunicadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMUNICADO")
    private Long idComunicado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_DOCENTE", nullable = false)
    private DocenteEntity docente;

    @Column(name = "COM_MENSAJE", nullable = false, length = 500)
    private String comMensaje;

    @Column(name = "COM_FECHA", nullable = false)
    private LocalDateTime comFecha;

    @Column(name = "COM_ACTIVO", nullable = false, length = 1)
    private String comActivo;

    @PrePersist
    public void alCrear() {
        if (comFecha == null) {
            comFecha = LocalDateTime.now();
        }

        if (comActivo == null) {
            comActivo = "S";
        }
    }
}
