package org.fitness.authservice.repositories;

import org.fitness.authservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String>{

    boolean existsByEmail(String email);

    Boolean existsByKeycloakId(String keycloakId);

    Optional<User> findByKeycloakId(String resourceId);

    Optional<User> findByEmail(String email);

    Optional<User> findTopByOrderByCreatedAtDesc();
}
