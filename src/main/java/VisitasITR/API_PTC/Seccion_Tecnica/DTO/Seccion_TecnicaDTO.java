package VisitasITR.API_PTC.Seccion_Tecnica.DTO;

import jakarta.validation.constraints.NotNull;

public class Seccion_TecnicaDTO {

    private Long idTecnica;

    @NotNull(message = "La técnica es obligatoria")
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
