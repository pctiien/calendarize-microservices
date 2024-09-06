package com.example.projectservice.service.project;

import com.example.projectservice.client.AuthServiceClient;
import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.dto.ProjectResponseDto;
import com.example.projectservice.dto.TeamMember;
import com.example.projectservice.dto.UserDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.TaskMember;
import com.example.projectservice.exception.ProjectNotFoundException;
import com.example.projectservice.mapper.ProjectMapper;
import com.example.projectservice.repository.ProjectRepository;
import com.example.projectservice.repository.ProjectTaskRepository;
import com.example.projectservice.repository.TaskMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {
    private final ProjectRepository projectRepository;
    private final TaskMemberRepository taskMemberRepository;
    private final AuthServiceClient authServiceClient;
    private final ProjectTaskRepository projectTaskRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(ProjectDto dto) {
        Project project = ProjectMapper.mapToProject(dto);
        projectRepository.save(project);
        addMemberToProject(project.getId(),project.getHostId());
        return project;
    }
    @Override
    public void addMemberToProject(Long taskId,Long userId)
    {
        taskMemberRepository.save(new TaskMember(taskId,userId));
    }

    @Override
    public ProjectResponseDto getProjectByProjectId(Long projectId) {

        Project project = projectRepository.findById(projectId).orElseThrow(()->new ProjectNotFoundException("id",projectId.toString()));

        List<TaskMember> projectMembers = taskMemberRepository.findAllByProjectId(projectId)
                .orElse(new ArrayList<>());

        List<Long> teamMemberIds = projectMembers.stream()
                .map(TaskMember::getUserId)
                .distinct()
                .toList();
        List<UserDto> members = teamMemberIds.stream().map(mem->authServiceClient.getUserById(mem).getBody()).toList();

        return ProjectResponseDto.builder()
                .id(project.getId())
                .hostId(project.getHostId())
                .description(project.getDescription())
                .name(project.getName())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .members(members.stream().map(
                        m->TeamMember.builder()
                                .id(m.getId())
                                .name(m.getName())
                                .email(m.getEmail())
                                .projectTasks(projectTaskRepository.findAllByUserId(m.getId()).orElse(new ArrayList<>()))
                                .build()
                ).toList())
                .build() ;
    }

    @Override
    public ProjectResponseDto getProjectByIdBetween(Long projectId, LocalDate from, LocalDate to) {
        LocalDateTime startDate = from.atStartOfDay();
        LocalDateTime endDate = to.atTime(LocalTime.MAX);

        Project project = projectRepository.findById(projectId).orElseThrow(()->new ProjectNotFoundException("id",projectId.toString()));

        List<TaskMember> projectMembers = taskMemberRepository.findAllByProjectId(projectId)
                .orElse(new ArrayList<>());

        List<Long> teamMemberIds = projectMembers.stream()
                .map(TaskMember::getUserId)
                .distinct()
                .toList();
        List<UserDto> members = teamMemberIds.stream().map(mem->authServiceClient.getUserById(mem).getBody()).toList();

        return ProjectResponseDto.builder()
                .id(project.getId())
                .hostId(project.getHostId())
                .description(project.getDescription())
                .name(project.getName())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .members(members.stream().map(
                        m->TeamMember.builder()
                                .id(m.getId())
                                .name(m.getName())
                                .email(m.getEmail())
                                .projectTasks(projectTaskRepository.findAllByUserIdBetween(m.getId(),startDate,endDate).orElse(new ArrayList<>()))
                                .build()
                ).toList())
                .build() ;
    }

    @Override
    public List<ProjectDto> getProjectsByUserId(Long userId) {
        List<Project> projects = projectRepository.findAllByUserId(userId).orElse(new ArrayList<>());
        return projects.stream().map(p -> ProjectDto.builder()
                .id(p.getId())
                .hostId(p.getHostId())
                .name(p.getName())
                .status(p.getStatus())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .build()
        ).collect(Collectors.toList()) ;
    }
}

