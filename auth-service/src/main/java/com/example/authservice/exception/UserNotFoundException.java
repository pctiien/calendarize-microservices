package com.example.authservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String fieldName, String fieldValue) {
        super(String.format("User not found with the given input data %s : '%s'", fieldName, fieldValue));
    }
}
