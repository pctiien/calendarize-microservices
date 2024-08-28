package com.example.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse{
    private Long id;
    private String name;
    private String email;
}