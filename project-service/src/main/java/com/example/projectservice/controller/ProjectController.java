package com.example.projectservice.controller;

import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.dto.ProjectResponseDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.service.project.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @GetMapping(params = "projectId")
    public ResponseEntity<ProjectResponseDto> getProjectByProject(@Param("projectId") Long projectId)
    {
        return ResponseEntity.ok(projectService.getProjectByProjectId(projectId));
    }
    @GetMapping(params = {"projectId","from","to"})
    public ResponseEntity<ProjectResponseDto> getProjectByIdBetween(
            @Param("projectId") Long projectId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to)
    {
        return ResponseEntity.ok(projectService.getProjectByIdBetween(projectId,from,to));
    }
    @GetMapping(params = "userId")
    public ResponseEntity<List<ProjectDto>> getProjectsByUser(@Param("userId") Long userId)
    {
        return ResponseEntity.ok(projectService.getProjectsByUserId(userId));
    }
    @PostMapping("add-member")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> addMemberToProject(@RequestBody Map<String,Object> requestData)
    {
        Long projectId = Long.valueOf(requestData.get("projectId").toString());
        String userEmail = requestData.get("userEmail").toString();
        projectService.addMemberToProject(projectId,userEmail);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("assign-role")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Project> assignRole(@RequestBody Map<String,Object> requestData)
    {
        Long projectId = Long.valueOf(requestData.get("projectId").toString());
        Long userId = Long.valueOf(requestData.get("userId").toString());
        Long roleId = Long.valueOf(requestData.get("projectRoleId").toString());
        return ResponseEntity.ok(projectService.assignRole(
                projectId,userId,roleId
        ));
    }



}
