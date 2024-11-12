package com.example.authservice.controller;

import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;
import com.example.authservice.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or (isAuthenticated() and principal.email == #email)")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(value = "email", required = false) String email) {
        if (email != null) {
            return ResponseEntity.ok(Collections.singletonList(userService.getUserByEmail(email)));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or (isAuthenticated() and hasAuthority('USER') and principal.id == #userId)")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN') or (isAuthenticated() and principal.email == #dto.email)")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto){
        return ResponseEntity.ok(userService.updateUser(dto));
    }





}
