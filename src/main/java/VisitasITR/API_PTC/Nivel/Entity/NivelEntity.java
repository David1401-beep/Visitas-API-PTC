package VisitasITR.API_PTC.Nivel.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "NIVEL",
        uniqueConstraints = @UniqueConstraint(name = "NIVEL_NOMBRE_UQ", columnNames = "NIVEL")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NivelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NIVEL")
    private Long idNivel;

    @Column(name = "NIVEL", nullable = false, length = 40)
    private String nivel;
}
