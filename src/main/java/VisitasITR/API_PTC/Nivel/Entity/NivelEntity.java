package VisitasITR.API_PTC.Nivel.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "NIVEL")
public class NivelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NIVEL")
    private Long idNivel;

    @Column(name = "NIVEL")
    private Integer nivel;
}