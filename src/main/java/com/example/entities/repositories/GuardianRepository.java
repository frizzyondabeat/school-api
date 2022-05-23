package com.example.entities.repositories;

import com.example.entities.models.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    boolean existsByName(String name);

    Optional<Guardian> findByName(String name);
}
