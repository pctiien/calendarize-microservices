package com.example.projectservice.controller;

import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.entity.ProjectTask;
import com.example.projectservice.service.project_task.IProjectTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/projects/tasks")
@RequiredArgsConstructor
public class ProjectTaskController {

    private final IProjectTaskService projectTaskService;
    @GetMapping
    public ResponseEntity<List<ProjectTask>> getAllProjectTask()
    {
        return ResponseEntity.ok(projectTaskService.getAllProjectTasks());
    }
    @GetMapping(params = "projectId")
    public ResponseEntity<List<ProjectTask>> getProjectTasksByProjectId(@RequestParam Long projectId)
    {
        return ResponseEntity.ok(projectTaskService.getProjectTasksByProjectId(projectId));
    }
    @PostMapping
    public ResponseEntity<ProjectTask> createProjectTask(@RequestBody ProjectTaskDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectTaskService.createProjectTask(dto));
    }
    @PutMapping("{projectTaskId}")
    public ResponseEntity<Void> doneProjectTask(@PathVariable Long projectTaskId)
    {
        projectTaskService.doneProjectTask(projectTaskId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("{projectTaskId}/user/{userId}")
    public ResponseEntity<Void> addMemberToProject(@PathVariable Long userId, @PathVariable Long projectTaskId)
    {
        projectTaskService.assignTo(projectTaskId,userId);
        return ResponseEntity.noContent().build();
    }



}
