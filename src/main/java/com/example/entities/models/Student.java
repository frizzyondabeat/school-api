package com.example.entities.models;

import com.example.entities.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity(name = "Student")
@Table(
        name = "student",
        schema = "db1",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_id_unique", columnNames = "email_address")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = "hibernateLazyInitializer")
public class Student implements Constants {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long studentId;

    @NotBlank(message = "First name should not be empty")
    @Column(
            name = "first_name",
            length = 50,
            nullable = false
    )
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    @Column(
            name = "last_name",
            length = 50,
            nullable = false
    )
    private String lastName;

    @NotBlank(message = "Email address must not be empty")
    @Email(message = "Email must be valid", regexp = REGEXP)
    @Column(
            name = "email_address",
            length = 50,
            nullable = false
    )
    private String emailAddress;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
            name = "guardian_id",
            referencedColumnName = "guardianId"
    )
    private Guardian guardian;



}
