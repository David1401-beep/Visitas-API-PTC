package VisitasITR.API_PTC.Seccion_Tecnica.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SECCION_TECNICA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeccionTecnicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TECNICA")
    private Long idTecnica;

    @Column(name = "TECNICA", nullable = false, unique = true, length = 20)
    private String tecnica;
}