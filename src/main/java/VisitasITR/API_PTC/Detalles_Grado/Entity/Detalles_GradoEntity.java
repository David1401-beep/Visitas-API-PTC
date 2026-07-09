package VisitasITR.API_PTC.Detalles_Grado.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@Table(name = "TBETALLES_GRADO")
public class Detalles_GradoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID_DETALLEGRADO")
    private Long id;

    @Column (name = "DETALLEGRADO")
    private Integer detalleGrado;

    @Column (name = "ID_GRUPO")
    private Long idGrupo;
}
