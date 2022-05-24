package com.example.entities.repositories;

import com.example.entities.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s.firstName, s.lastName from Student s where s.emailAddress = ?1")
    Optional<String> getStudentFirstNameAndLastNameByEmailAddress(String email);

    boolean existsByEmailAddress(String email);

    void deleteByEmailAddress(String emailAddress);

    @Modifying
    @Query("update Student s set s.firstName=?1, s.lastName=?2, s.emailAddress=?3 where s.emailAddress=?4")
    void updateFirstNameAndLastNameAndEmailAddressByEmailAddress(String firstName, String lastName, String emailAddress, String destinationEmail);


    Student findByEmailAddress(String email);
}
