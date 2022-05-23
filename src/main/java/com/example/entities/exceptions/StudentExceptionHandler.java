package com.example.entities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@ControllerAdvice
public class StudentExceptionHandler {

    @ExceptionHandler(value = {StudentNotFoundException.class})
    public ResponseEntity<Object> handleStudentRequestException(StudentNotFoundException exception){
        return new ResponseEntity<>(
                StudentExceptionDetails.builder()
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .time(ZonedDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {StudentBadRequestException.class})
    public ResponseEntity<Object> handleStudentRequestException(StudentBadRequestException exception){
        return new ResponseEntity<>(
                StudentExceptionDetails.builder()
                        .message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .time(ZonedDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
