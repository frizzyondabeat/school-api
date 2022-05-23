package com.example.entities.configurations;

import com.example.entities.models.*;
import com.example.entities.repositories.CourseMaterialRepository;
import com.example.entities.repositories.CourseRepository;
import com.example.entities.repositories.StudentRepository;
import com.example.entities.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Startup {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, CourseRepository courseRepository, CourseMaterialRepository courseMaterialRepository, TeacherRepository teacherRepository) {
        return args -> {
            studentRepository.saveAll(List.of(
                            Student.builder()
                                    .studentId(1L)
                                    .firstName("Efeoghene")
                                    .lastName("Omonigho")
                                    .emailAddress("frizzyondabeat@gmail.com")
                                    .guardian(Guardian.builder()
                                            .name("Nick Furry")
                                            .email("nickfury@gmail.com")
                                            .mobile("+234-907-811-7911")
                                            .build())
                                    .build(),
                            Student.builder()
                                    .studentId(2L)
                                    .firstName("Orezina")
                                    .lastName("Ogbokor")
                                    .emailAddress("ogbokor.orezina@lmu.edu.ng")
                                    .guardian(Guardian.builder()
                                            .name("Bruce Wayne")
                                            .email("batman@dc.com")
                                            .mobile("+234-67-643257")
                                            .build())
                                    .build()
                    )
            );

            courseMaterialRepository.save(
                    CourseMaterial.builder()
                            .url("https://www.google.com")
                            .course(Course.builder()
                                    .title("GEC")
                                    .credit(3)
                                    .teacher(Teacher.builder()
                                            .firstName("Speed")
                                            .lastName("Darlignton")
                                            .courses(List.of(Course.builder()
                                                    .title("GEC")
                                                    .credit(3)
                                                    .build())
                                            )
                                            .build())
                                    .build())
                            .build()
            );
        };
    }
}
