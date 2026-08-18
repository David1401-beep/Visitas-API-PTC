package VisitasITR.API_PTC.Usuarios.Repository;

import VisitasITR.API_PTC.Usuarios.Entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {

    boolean existsByUsuEmail(String usuEmail);

    Optional<UsuariosEntity> findByUsuEmail(String usuEmail);
}