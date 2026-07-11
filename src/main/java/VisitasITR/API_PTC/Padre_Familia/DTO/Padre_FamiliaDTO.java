package VisitasITR.API_PTC.Padre_Familia.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Padre_FamiliaDTO {


    private Long id;

    @NotNull(message = "ERRO1: El nombre del Padre es  obligatorio")
    private Integer Padre_Familia;


    @NotNull (message = "ERRO2: El telefono del padre obligatorio")
    private  Long PAD_nombre;
}

