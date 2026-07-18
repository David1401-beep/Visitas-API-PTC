package VisitasITR.API_PTC.Especialidad.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESPECIALIDAD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESPECIALIDAD")
    private Long idEspecialidad;

    @Column(name = "ESPECIALIDAD", nullable = false, length = 40) // Ej: "Desarrollo de Software"
    private String especialidad;
}