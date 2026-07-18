package VisitasITR.API_PTC.Encargado.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ENCARGADO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncargadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENCARGADO")
    private Long idEncargado;

    @Column(name = "ENC_NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "ENC_APELLIDO", nullable = false, length = 50)
    private String apellido;

    @Column(name = "ENC_TELEFONO", length = 9)
    private String telefono;

    @Column(name = "ENC_CORREO", length = 50)
    private String correo;
}