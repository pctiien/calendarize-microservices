package com.example.authservice.service.userService;

import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    UserDto updateUser(UserDto dto);
    UserDto getUserById(Long userId);
    User getUserByEmail(String email);
    List<User> getUserByEmail(List<String> emails);
}
