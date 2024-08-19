package com.example.projectservice.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String field,String value)
    {
        super(String.format("Project not found for field %s with value %s",field,value));
    }

}
