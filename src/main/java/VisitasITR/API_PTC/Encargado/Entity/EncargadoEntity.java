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
    private String encNombre;

    @Column(name = "ENC_APELLIDO", nullable = false, length = 50)
    private String encApellido;

    @Column(name = "ENC_TELEFONO", unique = true, length = 20)
    private String encTelefono;

    @Column(name = "ENC_TIPO", nullable = false, length = 30)
    private String encTipo;
}