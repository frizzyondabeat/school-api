package com.example.entities.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "hibernateLazyInitializer")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "role_sequence")
    private Long roleId;
    private String role;
}
