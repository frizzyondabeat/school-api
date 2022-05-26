package com.example.entities.repositories;

import com.example.entities.security.model.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@JsonIgnoreProperties(value = "hibernateLazyInitializer")
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRole(String roleName);

    Roles getByRole(String roleName);
}
