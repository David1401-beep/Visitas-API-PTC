package VisitasITR.API_PTC.Usuarios.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIOS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "USU_EMAIL", nullable = false, length = 100)
    private String usuEmail;

    @Column(name = "USU_PASSWORD", nullable = false, length = 255)
    private String usuPassword;

    @Column(name = "USU_ROL", nullable = false, length = 255)
    private String usuRol;
}