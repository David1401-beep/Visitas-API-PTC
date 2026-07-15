package VisitasITR.API_PTC.Especialidad.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ESPECIALIDAD")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESPECIALIDAD")
    private Integer idEspecialidad;

    @Column(name = "ESPECIALIDAD", nullable = false, length = 100)
    private String especialidad;

}

