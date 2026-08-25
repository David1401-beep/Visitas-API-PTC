package VisitasITR.API_PTC.Adminitrador.Controller;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ADMINISTRADOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADMINISTRADOR")
    private Long idAdministrador;

    @Column(name = "ADM_NOMBRES", nullable = false, length = 100)
    private String admNombres;

    @Column(name = "ADM_APELLIDOS", nullable = false, length = 100)
    private String admApellidos;

    @Column(name = "ADM_CORREO", nullable = false, unique = true, length = 150)
    private String admCorreo;

    @Column(name = "ADM_PASSWORD", nullable = false, length = 255)
    private String admPassword;

    @Column(name = "ADM_DUI", length = 10)
    private String admDui;

    @Column(name = "ADM_TELEFONO", length = 15)
    private String admTelefono;

    @Column(name = "ADM_ESTADO", nullable = false, length = 20)
    private String admEstado;

    @Column(name = "ADM_ROL", nullable = false, length = 30)
    private String admRol;
}