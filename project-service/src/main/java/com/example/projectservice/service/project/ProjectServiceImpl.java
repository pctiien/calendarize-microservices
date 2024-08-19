package com.example.projectservice.service.project;

import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectMember;
import com.example.projectservice.mapper.ProjectMapper;
import com.example.projectservice.repository.ProjectMemberRepository;
import com.example.projectservice.repository.ProjectRepository;
import com.example.projectservice.repository.ProjectTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectMemberRepository projectMemberRepository;

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
    public void addMemberToProject(Long projectId,Long userId)
    {
        projectMemberRepository.save(new ProjectMember(projectId,userId));
    }

    @Override
    public List<Project> getProjectsByUser(Long userId) {

        List<ProjectMember> projectMembers = projectMemberRepository.findAllByUserId(userId).orElse(new ArrayList<>());
        return projectMembers.stream()
                .map(pm->projectRepository.findById(pm.getProjectId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}

