package com.example.entities.repositories;

import com.example.entities.constants.TestConstants;
import com.example.entities.containers.MySqlTestContainer;
import com.example.entities.models.Guardian;
import com.example.entities.models.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
@Slf4j
class StudentRepositoryTest extends MySqlTestContainer implements TestConstants {

    @Autowired
    private StudentRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.save(Student.builder()
                        .firstName("Jessica")
                        .lastName("Okpegboro")
                        .emailAddress(ACTUAL_EMAIL)
                        .guardian(Guardian.builder()
                                .name("Odukoya Jane")
                                .email("odukoya.jane@ths.edu.ng")
                                .mobile("+234-678-432-612")
                                .build())
                .build());
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void updateFirstNameAndLastNameAndEmailAddressByEmailAddress() {
        underTest.updateFirstNameAndLastNameAndEmailAddressByEmailAddress(
                "Yusuf",
                "Caleb",
                TEST_EMAIL,
                ACTUAL_EMAIL);
        assertThat(underTest.getStudentFirstNameAndLastNameByEmailAddress(TEST_EMAIL)).isNotNull();
    }

    @Nested
    class whenDeletingByEmailAddress{
        @Test
        void deleteByEmailAddress() {
            underTest.deleteByEmailAddress(ACTUAL_EMAIL);
            assertThat(underTest.getByEmailAddress(ACTUAL_EMAIL)).isNull();
        }

    }


    @Nested
    class whenGettingStudentByEmail{
        @Test
        void getByEmailAddress() {
            assertThat(underTest.getByEmailAddress(ACTUAL_EMAIL)).isNotNull();
        }

        @Test
        void doNotGetByEmailAddress(){
            assertThat(underTest.getByEmailAddress(TEST_EMAIL)).isNull();
        }

    }


    @Nested
    class whenGettingNameByEmailAddress{
        @Test
        void canGetStudentNameByEmailAddress() {
            assertThat(underTest.getStudentFirstNameAndLastNameByEmailAddress(ACTUAL_EMAIL)).isPresent();
        }

        @Test
        void doNotGetStudent(){
            assertThat(underTest.getStudentFirstNameAndLastNameByEmailAddress(TEST_EMAIL)).isEmpty();
        }
    }

    @Nested
    class whenExistingByEmailAddress{
        @Test
        void canExistByEmailAddress(){
            assertThat(underTest.existsByEmailAddress(ACTUAL_EMAIL)).isTrue();
        }

        @Test
        void doNotExistByEmailAddress(){
            assertThat(underTest.existsByEmailAddress(TEST_EMAIL)).isFalse();
        }
    }

}