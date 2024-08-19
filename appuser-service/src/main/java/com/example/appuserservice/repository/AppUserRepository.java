package com.example.appuserservice.repository;

import com.example.appuserservice.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findAppUserByEmail(String email);
}
