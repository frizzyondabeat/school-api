package com.example.entities.services;

import com.example.entities.constants.Constants;
import com.example.entities.containers.MySqlTestContainer;
import com.example.entities.dto.StudentDto;
import com.example.entities.exceptions.StudentBadRequestException;
import com.example.entities.exceptions.StudentNotFoundException;
import com.example.entities.models.Guardian;
import com.example.entities.models.Student;
import com.example.entities.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;

import static com.example.entities.constants.TestConstants.ACTUAL_EMAIL;
import static com.example.entities.constants.TestConstants.TEST_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {StudentServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest extends MySqlTestContainer implements Constants {
    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Mock
    StudentRepository studentRepository;

    @Mock
    Logger logger;

    @InjectMocks
    StudentServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentServiceImpl(studentRepository);
    }

    /**
     * Method under test: {@link StudentServiceImpl#updateStudentByEmail(String, StudentDto)}
     */
    @Test
    void testUpdateStudentByEmail() {
        StudentDto dto = StudentDto.builder()
                .firstName("Oghenemine")
                .lastName("Oghorodi")
                .email("oghorodi.mine@babygirl.com")
                .build();
        given(studentRepository.existsByEmailAddress(ACTUAL_EMAIL)).willReturn(true);
        underTest.updateStudentByEmail(ACTUAL_EMAIL, dto);
        verify(studentRepository).updateFirstNameAndLastNameAndEmailAddressByEmailAddress(dto.getFirstName(), dto.getLastName(), dto.getEmail(), ACTUAL_EMAIL);
    }

    @Nested
    class whenGettingStudents {
        @Test
        void testGetAllStudents() {
            PageRequest pageable = PageRequest.of(0, 2, Sort.by("studentId"));
            underTest.getAllStudents(pageable);
            verify(studentRepository).findAll(pageable);
        }

        @Test
        @Disabled
        void testGetNameByEmailAddress() {
            String email = "frizzyondabeat@gmail.com";
            underTest.getNameByEmailAddress(email);
            ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
            verify(studentRepository).getStudentFirstNameAndLastNameByEmailAddress(stringArgumentCaptor.capture());
            String capturedString = stringArgumentCaptor.getValue();
            assertThat(capturedString).isEqualTo(email);

        }
    }

    @Nested
    class whenCreatingStudent {
        @Test
        void testCreateStudent() {
            StudentDto dto = StudentDto.builder()
                    .firstName("Oghenemine")
                    .lastName("Oghorodi")
                    .email("oghorodi.mine@babygirl.com")
                    .guardian(Guardian.builder()
                            .name("Mustafa Kareem")
                            .email("mk@yahoo.com")
                            .mobile("+234-57-683-4567")
                            .build())
                    .build();
            underTest.createStudent(dto);
            verify(studentRepository).save(any());
        }

        @Test
        void throwsException() {
            StudentDto dto = StudentDto.builder()
                    .firstName("Oghenemine")
                    .lastName("Oghorodi")
                    .email("oghorodi.mine@babygirl.com")
                    .guardian(Guardian.builder()
                            .name("Mustafa Kareem")
                            .email("mk@yahoo.com")
                            .mobile("+234-57-683-4567")
                            .build())
                    .build();
            when(studentRepository.existsByEmailAddress(dto.getEmail())).thenReturn(false);
            underTest.createStudent(dto);
            verify(studentRepository).save(any());
        }

    }

    @Nested
    class whenUpdatingStudent {
        @Test
        void updateStudentByEmail() {
            StudentDto dto = StudentDto.builder()
                    .firstName("Oghenemine")
                    .lastName("Oghorodi")
                    .email("oghorodi.mine@babygirl.com")
                    .guardian(Guardian.builder()
                            .name("Mustafa Kareem")
                            .email("mk@yahoo.com")
                            .mobile("+234-57-683-4567")
                            .build())
                    .build();
            given(studentRepository.existsByEmailAddress(ACTUAL_EMAIL)).willReturn(true);
            underTest.updateStudentByEmail(ACTUAL_EMAIL, dto);
            ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
            verify(studentRepository).getByEmailAddress(stringArgumentCaptor.capture());
            String capturedString = stringArgumentCaptor.getValue();
            assertThat(capturedString).isEqualTo(ACTUAL_EMAIL);
        }


        @Nested
        class whenDeletingStudent {
            @Test
            void deleteStudentByEmail() {
            }
        }

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme