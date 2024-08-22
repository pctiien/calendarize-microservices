package com.example.lifetaskservice.service;

import com.example.lifetaskservice.dto.LifeTaskDto;
import com.example.lifetaskservice.entity.LifeTask;

import java.time.LocalDate;
import java.util.List;

public interface ILifeTaskService {
    List<LifeTask> getLifeTasks();
    List<LifeTask> getLifeTasksByUserId(Long userId);
    LifeTask createLifeTask(LifeTaskDto dto);
    LifeTask doneTask(Long id);
    LifeTask updateTask(LifeTask lifeTask);
    List<List<LifeTask>> getLifeTaskBetween(Long userId ,LocalDate start, LocalDate end);
    void checkTaskIsOverdue();
}
