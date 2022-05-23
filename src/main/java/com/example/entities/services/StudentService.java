package com.example.entities.services;

import com.example.entities.dto.StudentDto;
import com.example.entities.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Page<Student> getAllStudents(Pageable pageable);

    String getNameByEmailAddress(String email);

    Student createStudent(StudentDto studentDto);

    Student updateStudentByEmail(String emailAddress, StudentDto studentDto);

    void deleteStudentByEmail(String emailAddress);
}
