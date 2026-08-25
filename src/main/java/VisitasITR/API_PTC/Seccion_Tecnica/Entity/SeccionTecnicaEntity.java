package VisitasITR.API_PTC.Seccion_Tecnica.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "SECCION_TECNICA",
        uniqueConstraints = @UniqueConstraint(name = "SECCION_TECNICA_UQ", columnNames = "TECNICA")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeccionTecnicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TECNICA")
    private Long idTecnica;

    @Column(name = "TECNICA", nullable = false, length = 20, unique = true)
    private String tecnica;
}