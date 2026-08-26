package VisitasITR.API_PTC.Recepcionista.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RECEPCIONISTA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionistaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECEPCIONISTA")
    private Long idRecepcionista;

    @Column(name = "REC_NOMBRE", nullable = false, length = 50)
    private String recNombre;

    @Column(name = "REC_APELLIDO", nullable = false, length = 50)
    private String recApellido;

    @Column(name = "REC_CORREO", nullable = false, unique = true, length = 100)
    private String recCorreo;

    @Column(name = "REC_PASSWORD", nullable = false, length = 100)
    private String recPassword;

    @Column(name = "REC_ROL", nullable = false, length = 25)
    private String recRol;
}