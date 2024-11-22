package com.example.projectservice.controller;

import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.dto.UserDto;
import com.example.projectservice.entity.ProjectTask;
import com.example.projectservice.service.project_task.IProjectTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/projects/tasks")
@RequiredArgsConstructor
public class ProjectTaskController {

    private final IProjectTaskService projectTaskService;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProjectTask>> getAllProjectTask()
    {
        return ResponseEntity.ok(projectTaskService.getAllProjectTasks());
    }
    @GetMapping(params = "projectId")
    @PreAuthorize("hasPermission(#projectId,'PROJECT','PROJECT_MEMBER')")
    public ResponseEntity<List<ProjectTask>> getProjectTasksByProjectId(@RequestParam Long projectId)
    {
        return ResponseEntity.ok(projectTaskService.getProjectTasksByProjectId(projectId));
    }
    @PostMapping
    @PreAuthorize("hasPermission(#dto.id,'TASK','PROJECT_CONTRIBUTOR')")
    public ResponseEntity<ProjectTask> createProjectTask(@RequestBody ProjectTaskDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectTaskService.createProjectTask(dto));
    }
    @PatchMapping("done-task")
    @PreAuthorize("hasPermission(#requestData['projectTaskId'],'TASK','PROJECT_MEMBER')")
    public ResponseEntity<Void> doneProjectTask(@RequestBody Map<String,Object> requestData)
    {
        Long projectTaskId = Long.valueOf(requestData.get("projectTaskId").toString());
        projectTaskService.doneProjectTask(projectTaskId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("{projectTaskId}/users/{userId}")
    public ResponseEntity<Void> addMemberToTask(@PathVariable Long userId, @PathVariable Long projectTaskId)
    {
        projectTaskService.assignTo(projectTaskId,userId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "add-member")
    @PreAuthorize("hasPermission(#requestData['projectTaskId'],'TASK','PROJECT_CONTRIBUTOR')")
    public ResponseEntity<Void> addMemberToTask(@RequestBody Map<String,Object> requestData)
    {
        String email = requestData.get("email").toString();
        Long projectTaskId = Long.valueOf(requestData.get("projectTaskId").toString());
        projectTaskService.assignTo(projectTaskId,email);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasPermission(#projectTaskId,'TASK','PROJECT_CONTRIBUTOR')")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long projectTaskId)
    {
        projectTaskService.deleteTask(projectTaskId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    @PreAuthorize("hasPermission(#dto.id,'TASK','PROJECT_CONTRIBUTOR')")
    public ResponseEntity<ProjectTask> editTask(@RequestBody ProjectTaskDto dto)
    {
        return ResponseEntity.ok(projectTaskService.editProjectTask(dto));
    }
    @GetMapping("{taskId}/users")
    @PreAuthorize("hasPermission(#taskId,'TASK','PROJECT_VIEWER') or hasPermission(#taskId,'TASK','PROJECT_MEMBER') ")

    public ResponseEntity<List<UserDto>> getUsersFromTaskId(@PathVariable Long taskId)
    {
        return ResponseEntity.ok(projectTaskService.getUsersFromTaskId(taskId));
    }





}
