package VisitasITR.API_PTC.Nivel.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NIVEL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NivelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NIVEL")
    private Long idNivel;

    @Column(name = "NIVEL", nullable = false, unique = true, length = 40)
    private String nivel;
}