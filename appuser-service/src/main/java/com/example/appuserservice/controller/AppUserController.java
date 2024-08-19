package com.example.appuserservice.controller;

import com.example.appuserservice.dto.AppUserDto;
import com.example.appuserservice.entity.AppUser;
import com.example.appuserservice.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers(){
        System.out.println("kaka");
           return ResponseEntity.ok(appUserService.getAllUsers());
    }
    @PostMapping
    public ResponseEntity<AppUserDto> updateUser(@RequestBody AppUserDto dto){
        appUserService.updateUser(dto);
        return ResponseEntity.ok(dto);
    }

}
