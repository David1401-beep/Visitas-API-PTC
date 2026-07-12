package VisitasITR.API_PTC.Padre_Familia.DTO;

import jakarta.validation.constraints.NotNull;

public class Padre_FamiliaDTO {

    private Long idPadre;

    @NotNull(message = "ERROR1: El nombre del padre es obligatorio")
    private String nombre;

    @NotNull(message = "ERROR2: El apellido del padre es obligatorio")
    private String apellido;

    @NotNull(message = "ERROR3: El teléfono del padre es obligatorio")
    private String telefono;

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}