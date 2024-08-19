package com.example.lifetaskservice.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String field,String value)
    {
        super(String.format("Task not found for field %s with value %s",field,value));
    }

}
