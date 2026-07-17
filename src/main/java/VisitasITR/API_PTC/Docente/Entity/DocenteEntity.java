package VisitasITR.API_PTC.Docente.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@Table(name = "DOCENTE")
public class DocenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOCENTE")
    private Long Id;

    @Column(name = "DOC_APELLIDO")
    private String docNombre;

    @Column(name = "DOC_APELLIDO")
    private String docApellido;

    @Column(name = "DOC_CORREO", unique = true)
    private String docCorreo;

    @Column(name = "DOC_ROL")
    private String docRol;
}
