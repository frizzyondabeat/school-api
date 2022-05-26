package com.example.entities.services;

import com.example.entities.dto.GuardianDto;
import com.example.entities.models.Guardian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuardianService {
    Page<Guardian> getAllGuardians(Pageable pageable);

    Guardian getGuardianByName(String name);

    Guardian updateGuardian(Long id, GuardianDto guardianDto);

    void deleteGuardianByEmail(String email);
}
