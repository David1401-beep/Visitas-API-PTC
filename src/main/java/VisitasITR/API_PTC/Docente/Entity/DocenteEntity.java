package VisitasITR.API_PTC.Docente.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DOCENTE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOCENTE")
    private Long idDocente;

    @Column(name = "DOC_NOMBRE", nullable = false, length = 50)
    private String docNombre;

    @Column(name = "DOC_APELLIDO", nullable = false, length = 50)
    private String docApellido;

    @Column(name = "DOC_CLAVE", nullable = false, length = 20)
    private String docClave;

    @Column(name = "DOC_CORREO", nullable = false, unique = true, length = 100)
    private String docCorreo;

    @Column(name = "DOC_PASSWORD", nullable = false, length = 100)
    private String docPassword;

    @Column(name = "DOC_TIPO", nullable = false, length = 50)
    private String docTipo;

    @Column(name = "DOC_ROL", nullable = false, length = 25)
    private String docRol;
}