package com.example.entities.repositories;

import com.example.entities.models.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    Optional<Guardian> findByName(String name);

    @Modifying
    @Query("update Guardian g set g.name=?2, g.email=?3, g.mobile=?4 where g.guardianId=?1")
    void updateGuardianById(Long id, String name, String email, String mobile);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
