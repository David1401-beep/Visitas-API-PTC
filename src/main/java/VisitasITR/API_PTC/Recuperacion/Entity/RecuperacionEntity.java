package VisitasITR.API_PTC.Recuperacion.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Un codigo de recuperacion. El codigo va cifrado, nunca en texto plano.
@Entity
@Table(name = "RECUPERACION_PASSWORD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecuperacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECUPERACION")
    private Long id;

    @Column(name = "REC_CORREO", nullable = false)
    private String correo;

    @Column(name = "REC_CODIGO_HASH", nullable = false)
    private String codigoHash;

    @Column(name = "REC_EXPIRA", nullable = false)
    private LocalDateTime expira;

    @Column(name = "REC_USADO", nullable = false)
    private Integer usado = 0;

    @Column(name = "REC_INTENTOS", nullable = false)
    private Integer intentos = 0;

    @Column(name = "REC_CREADO", nullable = false)
    private LocalDateTime creado;

    public boolean estaVencido() {
        return LocalDateTime.now().isAfter(expira);
    }

    public boolean fueUsado() {
        return usado != null && usado == 1;
    }
}
