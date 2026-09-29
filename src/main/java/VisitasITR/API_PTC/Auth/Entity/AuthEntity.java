package VisitasITR.API_PTC.Auth.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_USUARIOS_AUTH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthEntity {

    @Column(name = "ID_USUARIO")
    private Long id;

    @Id
    @Column(name = "USU_EMAIL")
    private String email;

    @Column(name = "USU_PASSWORD")
    private String password;

    @Column(name = "USU_ROL")
    private String rol;

    // De que tabla salio la fila. Lo necesito para saber donde escribir
    // cuando alguien cambia su contrasena, porque la vista no acepta UPDATE.
    @Column(name = "USU_ORIGEN")
    private String origen;
}