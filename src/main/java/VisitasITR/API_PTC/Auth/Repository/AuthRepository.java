package VisitasITR.API_PTC.Auth.Repository;

import VisitasITR.API_PTC.Auth.Entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, String> {

    Optional<AuthEntity> findByEmailIgnoreCase(String email);
}