package VisitasITR.API_PTC.Administrador.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADMINISTRADOR")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADMINISTRADOR")
    private Long idAdministrador;

    @Column(name = "ADM_NOMBRE", nullable = false, length = 50)
    private String admNombre;

    @Column(name = "ADM_APELLIDO", nullable = false, length = 50)
    private String admApellido;

    @Column(name = "ADM_CORREO", nullable = false, unique = true, length = 100)
    private String admCorreo;

    @Column(name = "ADM_PASSWORD", nullable = false, length = 100)
    private String admPassword;

    @Column(name = "ADM_ROL", nullable = false, length = 25)
    private String admRol;
}