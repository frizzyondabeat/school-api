package com.example.entities.services;

import com.example.entities.dto.StudentDto;
import com.example.entities.exceptions.StudentBadRequestException;
import com.example.entities.exceptions.StudentNotFoundException;
import com.example.entities.models.Guardian;
import com.example.entities.models.Student;
import com.example.entities.repositories.GuardianRepository;
import com.example.entities.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final GuardianRepository guardianRepository;

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public String getNameByEmailAddress(String email) {
        return studentRepository.getStudentFirstNameAndLastNameByEmailAddress(email).orElseThrow(() -> new StudentNotFoundException("Student Not Found")).replace(",", " ");
    }

    @Override
    @Transactional
    public Student createStudent(StudentDto studentDto) {
        if (studentRepository.existsByEmailAddress(studentDto.getEmail())){
            log.error("Student already exists");
            throw new StudentBadRequestException("Student already exists");
        }
        return studentRepository.save(Student.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .emailAddress(studentDto.getEmail())
                .guardian(guardianRepository.findByName(studentDto.getGuardian().getName())
                        .orElse(Guardian.builder()
                                .name(studentDto.getGuardian().getName())
                                .email(studentDto.getGuardian().getEmail())
                                .mobile(studentDto.getGuardian().getMobile())
                                .build()))
                .build());
    }

    @Override
    @Transactional
    public Student updateStudentByEmail(String emailAddress, StudentDto studentDto) {
        if (!studentRepository.existsByEmailAddress(emailAddress)){
            log.error("Student does not exist");
            throw new StudentNotFoundException("No such student with email " + emailAddress + " exists");
        }
        studentRepository.updateFirstNameAndLastNameAndEmailAddressByEmailAddress(
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getEmail(),
                emailAddress);
        return studentRepository.getByEmailAddress(studentDto.getEmail());
    }

    @Override
    @Transactional
    public void deleteStudentByEmail(String emailAddress) {
        if (!studentRepository.existsByEmailAddress(emailAddress)){
            log.error("Student does not exist");
            throw new StudentNotFoundException("No such student with email " + emailAddress + " exists");
        }
        studentRepository.deleteByEmailAddress(emailAddress);
        log.info("Student deleted successfully");
    }

}
