package com.example.entities.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class StudentExceptionDetails {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime time;
}
