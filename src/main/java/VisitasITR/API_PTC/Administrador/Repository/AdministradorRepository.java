package VisitasITR.API_PTC.Administrador.Repository;

import VisitasITR.API_PTC.Administrador.Entity.AdministradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorEntity, Long> {

    boolean existsByAdmCorreo(String admCorreo);

    boolean existsByAdmCorreoAndIdAdministradorNot(String admCorreo, Long idAdministrador);
}