package VisitasITR.API_PTC.Docente.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DOCENTE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOCENTE")
    private Long idDocente;

    @Column(name = "DOC_NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "DOC_APELLIDO", nullable = false, length = 50)
    private String apellido;
}
