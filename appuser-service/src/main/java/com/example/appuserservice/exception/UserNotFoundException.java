package com.example.appuserservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
