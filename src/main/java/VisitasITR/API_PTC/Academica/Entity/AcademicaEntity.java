package VisitasITR.API_PTC.Academica.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACADEMICA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Academica")
    private Long idAcademica;

    @Column(name = "Academica")
    private String seccion;
}