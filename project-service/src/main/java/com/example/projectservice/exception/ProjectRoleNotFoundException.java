package com.example.projectservice.exception;

public class ProjectRoleNotFoundException extends RuntimeException{
    public ProjectRoleNotFoundException(String field,String value)
    {
        super(String.format("Project role not found for field %s with value %s",field,value));
    }

}
