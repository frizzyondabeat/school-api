package com.example.entities.controllers;

import com.example.entities.dto.GuardianDto;
import com.example.entities.services.GuardianService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/guardian/")
@RequiredArgsConstructor
@Slf4j
public class GuardianController {

    private final GuardianService guardianService;

    @GetMapping
    public ResponseEntity<Object> getAllGuardians(
            @PageableDefault(sort = "name") Pageable pageable
    ){
        try {
            log.info("Fetching all guardians.....");
            return ResponseEntity.ok().body(guardianService.getAllGuardians(pageable).getContent());
        } catch (RuntimeException exception){
            log.error("Error fetching all guardians");
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getGuardianByName(
            @PathVariable(value = "name") String name
    ){
        try {
            log.info("Attempting to fetch guardian [{}]", name);
            return ResponseEntity.ok().body(guardianService.getGuardianByName(name));
        } catch (RuntimeException exception){
            log.error("Guardian not found.\nMessage:{}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{guardianId}")
    public ResponseEntity<Object> updateGuardianById(
            @PathVariable(value = "guardianId") Long id,
            @Valid @RequestBody GuardianDto guardianDto
    ){
        try {
            log.info("Attempting to update Guardian[{}]", guardianDto.getName());
            return ResponseEntity.ok().body(guardianService.updateGuardian(id, guardianDto));
        } catch (RuntimeException exception){
            log.error("Error updating guardian [{}]", guardianDto.getName());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("{email}")
    public ResponseEntity<Object> deleteGuardianByEmail(
            @PathVariable(value = "email") String email
    ){
        try {
            log.info("Attempting to delete guardian [{}]", email);
            guardianService.deleteGuardianByEmail(email);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception){
            log.error("Error deleting guardian.\nMessage:{}", exception.getMessage());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
