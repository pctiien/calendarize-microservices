package com.example.projectservice.controller;

import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.dto.UserDto;
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
    @PostMapping("{projectTaskId}/users/{userId}")
    public ResponseEntity<Void> addMemberToProject(@PathVariable Long userId, @PathVariable Long projectTaskId)
    {
        projectTaskService.assignTo(projectTaskId,userId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "{projectTaskId}/users",params= "email")
    public ResponseEntity<Void> addMemberToProject(@RequestParam String email , @PathVariable Long projectTaskId)
    {
        projectTaskService.assignTo(projectTaskId,email);
        return ResponseEntity.noContent().build();
    }
//    @PostMapping(value = "{projectTaskId}/users",params= "emails")
//    public ResponseEntity<Void> addMemberToProject(@RequestParam("emails") List<String> emails , @PathVariable Long projectTaskId)
//    {
//        projectTaskService.assignTo(projectTaskId,emails);
//        return ResponseEntity.noContent().build();
//    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long projectTaskId)
    {
        projectTaskService.deleteTask(projectTaskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<ProjectTask> editTask(@RequestBody ProjectTaskDto dto)
    {
        return ResponseEntity.ok(projectTaskService.editProjectTask(dto));
    }
    @GetMapping("{taskId}/users")
    public ResponseEntity<List<UserDto>> getUsersFromTaskId(@PathVariable Long taskId)
    {
        return ResponseEntity.ok(projectTaskService.getUsersFromTaskId(taskId));
    }





}
