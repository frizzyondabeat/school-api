package com.example.entities.exceptions;

public class ApiBadRequestException extends RuntimeException{
    public ApiBadRequestException(String message) {
        super(message);
    }
}
