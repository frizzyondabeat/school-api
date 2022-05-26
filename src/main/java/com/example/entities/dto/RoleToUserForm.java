package com.example.entities.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RoleToUserForm {
    @NotBlank
    private String email;
    @NotBlank
    private String roleName;
}
