package VisitasITR.API_PTC.Padre_Familia.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PADRE_FAMILIA")
public class Padre_FamiliaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PADRE")
    private Long idPadre;

    @Column(name = "PAD_NOMBRE")
    private String nombre;

    @Column(name = "PAD_APELLIDO")
    private String apellido;

    @Column(name = "PAD_TELEFONO")
    private String telefono;

    // Getters y Setters

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