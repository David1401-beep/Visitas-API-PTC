package VisitasITR.API_PTC.Imagen.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "IMAGEN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMAGEN")
    private Long idImagen;

    @Column(name = "IMG_NOMBRE", length = 200)
    private String imgNombre;

    //Direccion de la imagen en Cloudinary.
    @Column(name = "IMG_URL", nullable = false, length = 300)
    private String imgUrl;

    //Identificador que me da Cloudinary, lo necesito para borrarla.
    @Column(name = "IMG_CLOUDINARY_ID", nullable = false, length = 200)
    private String imgCloudinaryId;

    @Column(name = "IMG_FECHA", nullable = false)
    private LocalDateTime imgFecha;

    @PrePersist
    public void alCrear() {
        if (imgFecha == null) {
            imgFecha = LocalDateTime.now();
        }
    }
}
