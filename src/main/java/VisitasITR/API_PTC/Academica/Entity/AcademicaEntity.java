package VisitasITR.API_PTC.Academica.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ACADEMICA")
@Getter
@Setter
public class AcademicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Academica")
    private Long idAcademica;

    @Column(name = "Academica", nullable = false)
    private String academica;
}
