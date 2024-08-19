package com.example.lifetaskservice.service;

import com.example.lifetaskservice.dto.LifeTaskDto;
import com.example.lifetaskservice.entity.LifeTask;

import java.util.List;

public interface ILifeTaskService {
    List<LifeTask> getLifeTasks();
    LifeTask createLifeTask(LifeTaskDto dto);
    LifeTask doneTask(Long id);
    LifeTask updateTask(LifeTask lifeTask);
}
