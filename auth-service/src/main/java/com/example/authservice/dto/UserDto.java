package com.example.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private String name;
    private String email;
    private String imageUrl;

}