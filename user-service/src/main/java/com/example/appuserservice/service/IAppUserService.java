package com.example.appuserservice.service;

import com.example.appuserservice.dto.AppUserDto;
import com.example.appuserservice.entity.AppUser;

import java.util.List;

public interface IAppUserService {
    List<AppUser> getAllUsers();
    AppUserDto updateUser(AppUserDto dto);
    AppUserDto getUserById(Long userId);
}
