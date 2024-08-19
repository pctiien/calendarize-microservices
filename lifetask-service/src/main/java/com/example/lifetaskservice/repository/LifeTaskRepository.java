package com.example.lifetaskservice.repository;

import com.example.lifetaskservice.entity.LifeTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeTaskRepository extends JpaRepository<LifeTask,Long> {
}
