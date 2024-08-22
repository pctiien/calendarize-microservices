package com.example.lifetaskservice.repository;

import com.example.lifetaskservice.entity.LifeTask;
import com.example.lifetaskservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LifeTaskRepository extends JpaRepository<LifeTask,Long> {
    Optional<List<LifeTask>> findLifeTasksByUserId(Long userId);
    Optional<List<LifeTask>> findAllByUserIdAndStartDateBetween(Long userId, LocalDateTime startDate, LocalDateTime startDate2);
    Optional<List<LifeTask>> findByEndDateBeforeAndStatusIn(LocalDateTime endDate, List<Status> statuses);
}
