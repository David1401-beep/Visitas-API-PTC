package VisitasITR.API_PTC.Empleado.Repository;

import VisitasITR.API_PTC.Empleado.Entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {

    boolean existsByEmpCorreo(String empCorreo);

    Optional<EmpleadoEntity> findByEmpCorreo(String empCorreo);
}