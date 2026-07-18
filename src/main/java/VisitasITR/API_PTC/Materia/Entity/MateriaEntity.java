package VisitasITR.API_PTC.Materia.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MATERIA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MATERIA")
    private Long idMateria;

    @Column(name = "MAT_NOMBRE", nullable = false, length = 80)
    private String nombre;

    @Column(name = "MAT_TIPO", nullable = false, length = 20)
    private String tipo;
}