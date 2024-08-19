package com.example.appuserservice.mapper;

import com.example.appuserservice.dto.AppUserDto;
import com.example.appuserservice.entity.AppUser;

public class AppUserMapper {
    public static AppUser toEntity(AppUserDto dto) {
        return AppUser.builder().email(dto.getEmail()).name(dto.getName()).password(dto.getPassword())
                .build();
    }
}
