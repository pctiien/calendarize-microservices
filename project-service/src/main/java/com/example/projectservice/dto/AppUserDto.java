package com.example.projectservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserDto {
    private String name;
    private String email;
    private String password;

}