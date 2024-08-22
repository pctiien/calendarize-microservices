package com.example.projectservice.controller;

import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.dto.ProjectResponseDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.service.project.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final IProjectService projectService;
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects()
    {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(dto));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByUserId(@PathVariable Long userId)
    {
        return ResponseEntity.ok(projectService.getProjectsByUser(userId));
    }
    @PostMapping("{projectId}/user/{userId}")
    public ResponseEntity<Void> addMemberToProject(@PathVariable Long projectId,@PathVariable Long userId)
    {
        projectService.addMemberToProject(projectId,userId);
        return ResponseEntity.noContent().build();
    }



}
