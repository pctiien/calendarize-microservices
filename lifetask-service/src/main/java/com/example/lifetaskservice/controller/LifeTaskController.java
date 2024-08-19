package com.example.lifetaskservice.controller;

import com.example.lifetaskservice.dto.LifeTaskDto;
import com.example.lifetaskservice.entity.LifeTask;
import com.example.lifetaskservice.service.ILifeTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/life/tasks")
@RequiredArgsConstructor
public class LifeTaskController {
    private final ILifeTaskService lifeTaskService;
    @GetMapping
    public ResponseEntity<List<LifeTask>> getLifeTasks()
    {
        return ResponseEntity.ok(lifeTaskService.getLifeTasks());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<LifeTask>> getLifeTasksByUserId(@PathVariable Long userId)
    {
        return ResponseEntity.ok(lifeTaskService.getLifeTasksByUserId(userId));
    }
    @PostMapping
    public ResponseEntity<LifeTask> createLifeTask(@RequestBody LifeTaskDto lifeTaskDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(lifeTaskService.createLifeTask(lifeTaskDto));
    }
    @PutMapping("/{lifeTaskId}")
    public ResponseEntity<LifeTask> doneTask(@PathVariable Long lifeTaskId)
    {
        return ResponseEntity.ok(lifeTaskService.doneTask(lifeTaskId));
    }

    @PutMapping
    public ResponseEntity<LifeTask> updateTask(@RequestBody LifeTask lifeTask)
    {
        return ResponseEntity.ok(lifeTaskService.updateTask(lifeTask));
    }


}