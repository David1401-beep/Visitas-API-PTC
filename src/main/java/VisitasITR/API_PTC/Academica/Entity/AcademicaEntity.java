package VisitasITR.API_PTC.Academica.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACADEMICA", uniqueConstraints = @UniqueConstraint(name = "ACADEMICA_NOMBRE_UQ", columnNames = "ACADEMICA"))
public class AcademicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACADEMICA")
    private Long idAcademica;

    @Column(name = "ACADEMICA", nullable = false, length = 60)
    private String seccion;
}