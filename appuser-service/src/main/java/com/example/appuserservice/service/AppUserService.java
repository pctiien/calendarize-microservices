package com.example.appuserservice.service;

import com.example.appuserservice.dto.AppUserDto;
import com.example.appuserservice.entity.AppUser;
import com.example.appuserservice.exception.UserNotFoundException;
import com.example.appuserservice.mapper.AppUserMapper;
import com.example.appuserservice.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService{

    private final AppUserRepository appUserRepository;
    @Override
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUserDto updateUser(AppUserDto dto) {
        Optional<AppUser> optionalAppUser = appUserRepository.findAppUserByEmail(dto.getEmail());
        if(optionalAppUser.isEmpty()) {
            throw new UserNotFoundException("email",dto.getEmail());
        }
        AppUser user = AppUserMapper.toEntity(dto);
        user.setId(optionalAppUser.get().getId());
        appUserRepository.save(user);
        return dto;
    }

    @Override
    public AppUserDto getUserById(Long userId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("id",userId.toString()));
        return AppUserMapper.toDto(user);
    }
}
