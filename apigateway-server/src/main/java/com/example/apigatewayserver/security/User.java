package com.example.apigatewayserver.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User{
    private Long id;
    private String name;
    private String email;
}