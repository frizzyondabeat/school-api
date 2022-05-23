package com.example.entities.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Teacher {

    @Id
    @SequenceGenerator(
            name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teacher_sequence")
    private Long teacherId;

    @NotBlank(message = "First name cannot be empty")
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @NotBlank(message = "First")
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;


}
