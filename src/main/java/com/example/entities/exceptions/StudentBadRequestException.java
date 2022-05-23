package com.example.entities.exceptions;

public class StudentBadRequestException extends RuntimeException{
    public StudentBadRequestException(String message) {
        super(message);
    }
}
