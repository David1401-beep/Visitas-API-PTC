package VisitasITR.API_PTC.Academica.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ACADEMICA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACADEMICA")
    private Long idAcademica;

    @Column(name = "SECCION", nullable = false, length = 2) // Ej: "A", "B", "C"
    private String seccion;
}
