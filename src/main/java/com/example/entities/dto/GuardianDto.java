package com.example.entities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuardianDto {

    private String name;
    private String email;
    private String mobile;
}
