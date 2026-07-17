package VisitasITR.API_PTC.Seccion_Tecnica.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SECCION_TECNICA")
public class Seccion_TecnicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TECNICA")
    private Long idTecnica;

    @Column(name = "TECNICA")
    private String tecnica;

    public Long getIdTecnica() {
        return idTecnica;
    }

    public void setIdTecnica(Long idTecnica) {
        this.idTecnica = idTecnica;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }
}