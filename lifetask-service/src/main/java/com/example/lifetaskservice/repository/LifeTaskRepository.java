package com.example.lifetaskservice.repository;

import com.example.lifetaskservice.entity.LifeTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LifeTaskRepository extends JpaRepository<LifeTask,Long> {
    Optional<List<LifeTask>> findLifeTasksByUserId(Long userId);
}