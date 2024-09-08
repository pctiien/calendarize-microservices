package com.example.projectservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String field,String value){
        super(String.format("Task not found for field %s with value %s",field,value));
    }

}