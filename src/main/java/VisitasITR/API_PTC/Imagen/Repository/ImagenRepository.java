package VisitasITR.API_PTC.Imagen.Repository;

import VisitasITR.API_PTC.Imagen.Entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
