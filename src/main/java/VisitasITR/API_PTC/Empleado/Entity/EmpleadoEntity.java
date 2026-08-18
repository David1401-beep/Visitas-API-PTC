package VisitasITR.API_PTC.Empleado.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "EMPLEADOS",
        uniqueConstraints = @UniqueConstraint(name = "EMPLEADO_CORREO_UQ", columnNames = "EMP_CORREO")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPLEADO")
    private Long idEmpleado;

    @Column(name = "EMP_NOMBRE", nullable = false, length = 50)
    private String empNombre;

    @Column(name = "EMP_APELLIDO", nullable = false, length = 50)
    private String empApellido;

    @Column(name = "EMP_CLAVE", nullable = false, length = 20)
    private String empClave;

    @Column(name = "EMP_CORREO", nullable = false, length = 100, unique = true)
    private String empCorreo;

    @Column(name = "EMP_ROL", nullable = false, length = 50)
    private String empRol;

    @Column(name = "USUARIO_EMPLEADO", nullable = false)
    private Long usuarioEmpleado;
}