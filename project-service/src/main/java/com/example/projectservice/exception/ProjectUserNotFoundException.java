package com.example.projectservice.exception;

public class ProjectUserNotFoundException extends RuntimeException{
    public ProjectUserNotFoundException(String field,String value)
    {
        super(String.format("User for field %s with value %s are not in project",field,value));
    }

}