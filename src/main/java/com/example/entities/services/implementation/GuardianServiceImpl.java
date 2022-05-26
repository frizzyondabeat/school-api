package com.example.entities.services.implementation;

import com.example.entities.dto.GuardianDto;
import com.example.entities.exceptions.ApiNotFoundException;
import com.example.entities.models.Guardian;
import com.example.entities.repositories.GuardianRepository;
import com.example.entities.services.GuardianService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository guardianRepository;
    @Override
    public Page<Guardian> getAllGuardians(Pageable pageable) {
        return guardianRepository.findAll(pageable);
    }

    @Override
    public Guardian getGuardianByName(String name) {
        return guardianRepository.findByName(name).orElseThrow(() -> new ApiNotFoundException("Guardian not found"));
    }

    @Override
    @Transactional
    public Guardian updateGuardian(Long id, GuardianDto guardianDto) {
        if (!guardianRepository.existsById(id))
            throw new ApiNotFoundException("Guardian at id:" + id + " does not exist");
        guardianRepository.updateGuardianById(id, guardianDto.getName(), guardianDto.getEmail(), guardianDto.getMobile());
        return guardianRepository.getById(id);
    }

    @Override
    @Transactional
    public void deleteGuardianByEmail(String email) {
        if (!guardianRepository.existsByEmail(email))
            throw new ApiNotFoundException("Guardian with email: " + email + " doesn't exist");
        guardianRepository.deleteByEmail(email);
        log.info("Deleted guardian [{}] successfully", email);
    }

}
