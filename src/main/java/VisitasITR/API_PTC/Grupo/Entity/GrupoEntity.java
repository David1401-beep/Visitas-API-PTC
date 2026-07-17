package VisitasITR.API_PTC.Grupo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "GRUPO")
public class GrupoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GRUPO")
    private Long idGrupo;

    @Column(name = "GRUPO")
    private Integer grupo;

    @Column(name = "IDNIVEL")
    private Long idNivel;

    @Column(name = "IDESPECIALIDAD")
    private Long idEspecialidad;

    @Column(name = "IDACADEMICA")
    private Long idAcademica;

    @Column(name = "ID_TECNICA")
    private Long idTecnica;
}
