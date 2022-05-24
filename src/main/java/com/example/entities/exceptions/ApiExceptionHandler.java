package com.example.entities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleStudentRequestException(ApiNotFoundException exception){
        return new ResponseEntity<>(
                ApiExceptionDetails.builder()
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .time(ZonedDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {ApiBadRequestException.class})
    public ResponseEntity<Object> handleStudentRequestException(ApiBadRequestException exception){
        return new ResponseEntity<>(
                ApiExceptionDetails.builder()
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .time(ZonedDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
