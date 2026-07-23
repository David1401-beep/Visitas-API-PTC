package VisitasITR.API_PTC.Academica.Entity;

import VisitasITR.API_PTC.Detalle_Grado.Entity.DetalleGradoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACADEMICA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Academica")
    private Long idAcademica;

    @Column(name = "Academica")
    private String seccion;

    @OneToMany(mappedBy = "academica", fetch = FetchType.LAZY)
    List<DetalleGradoEntity> listaGrado = new ArrayList<>();
}