package com.example.entities.controllers;

import com.example.entities.dto.StudentDto;
import com.example.entities.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/student/")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<Object> getAllStudents(
            @PageableDefault(sort = {"lastName", "firstName"}) Pageable page
            ){
        try {
            log.info("Fetching all students.....");
            return new ResponseEntity<>(studentService.getAllStudents(page).getContent(), HttpStatus.OK);
        } catch (RuntimeException exception){
            log.error("Error getting students.\nMessage: {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{email}")
    public ResponseEntity<String> getNameByEmailAddress(
            @PathVariable(name = "email") String email
    ){
        try {
            log.info("Fetching Student with email {}", email);
            return new ResponseEntity<>(studentService.getNameByEmailAddress(email), HttpStatus.OK);
        } catch (RuntimeException exception){
            log.error("Error fetching student with email: {}\nMessage: {}", email, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(
            @Valid @RequestBody StudentDto studentDto
    ){
        try {
            log.info("Creating new student with email {}", studentDto.getEmail());
            return new ResponseEntity<>(studentService.createStudent(studentDto), HttpStatus.CREATED);
        } catch (RuntimeException exception){
            log.error("Error creating new student\nMessage: {}", exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{emailAddress}")
    public ResponseEntity<Object> updateStudent(
            @PathVariable(name = "emailAddress") String emailAddress,
            @Valid @RequestBody StudentDto studentDto
    ){
        try {
            log.info("Attempting to update student with email {}", emailAddress);
            return new ResponseEntity<>(studentService.updateStudentByEmail(emailAddress, studentDto), HttpStatus.CREATED);
        } catch (RuntimeException exception){
            log.error("Error updating student with email {}\nMessage: {}", emailAddress, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{emailAddress}")
    public ResponseEntity<Object> deleteStudent(
            @PathVariable(name = "emailAddress") String emailAddress
    ){
        try {
            log.info("Attempting to delete student with email {}", emailAddress);
            studentService.deleteStudentByEmail(emailAddress);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException exception){
            log.error("Error deleting student with email {}\nMessage: {}", emailAddress, exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
