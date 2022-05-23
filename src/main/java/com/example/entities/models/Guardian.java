package com.example.entities.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "guardian_email_unique",
                        columnNames = "guardian_email"
                ),
                @UniqueConstraint(
                        name = "guardian_name_unique",
                        columnNames = "guardian_name"
                ),
                @UniqueConstraint(
                        name = "guardian_mobile_unique",
                        columnNames = "guardian_mobile"
                )
        }
)
public class Guardian {

    @Id
    @SequenceGenerator(
            name = "guardian_sequence",
            sequenceName = "guardian_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guardian_sequence"
    )
    private Long guardianId;

    @NotBlank
    @Column(
            name = "guardian_name",
            length = 50
    )
    private String name;

    @NotBlank
    @Column(
            name = "guardian_email",
            length = 50
    )
    private String email;

    @NotBlank
    @Column(
            name = "guardian_mobile",
            length = 50
    )
    private String mobile;

    @JsonBackReference
    @OneToMany(mappedBy = "guardian", fetch = FetchType.EAGER)
    @Convert
    private List<Student> students;

    public void addStudents(Student student){
        students.add(student);
    }
}
