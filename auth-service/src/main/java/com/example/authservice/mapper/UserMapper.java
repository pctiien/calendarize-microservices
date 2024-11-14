package com.example.authservice.mapper;

import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;

public class UserMapper {
    public static User toEntity(UserDto dto) {
        return User.builder().email(dto.getEmail()).name(dto.getName()).imageUrl(dto.getImageUrl())
                .build();
    }
    public static UserDto toDto(User user)
    {
        return UserDto.builder().id(user.getId()).email(user.getEmail())
                .name(user.getName()).build();
    }

}
