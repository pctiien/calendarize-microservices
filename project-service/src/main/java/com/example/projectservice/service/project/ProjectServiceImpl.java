package com.example.projectservice.service.project;

import com.example.projectservice.client.AuthServiceClient;
import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.dto.ProjectResponseDto;
import com.example.projectservice.dto.UserDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectMember;
import com.example.projectservice.mapper.ProjectMapper;
import com.example.projectservice.repository.ProjectMemberRepository;
import com.example.projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthServiceClient authServiceClient;

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
    public List<ProjectResponseDto> getProjectsByUser(Long userId) {

        List<ProjectMember> projectMembers = projectMemberRepository.findAllByUserId(userId)
                .orElse(new ArrayList<>());

        List<Long> projectIds = projectMembers.stream()
                .map(ProjectMember::getProjectId)
                .collect(Collectors.toList());

        List<Project> projects = projectRepository.findAllById(projectIds);

        Map<Long, List<UserDto>> projectIdToUsersMap = new HashMap<>();

        for (Long projectId : projectIds) {

            List<Long> memberIds = projectMemberRepository.findAllByProjectId(projectId).orElse(new ArrayList<>())
                    .stream().map(ProjectMember::getUserId).toList();

            if(!projectIdToUsersMap.containsKey(projectId))
            {
                projectIdToUsersMap.put(projectId,new ArrayList<>());
            }

            memberIds.forEach(id->projectIdToUsersMap.get(projectId).add(authServiceClient.getUserById(id).getBody()));

        }

        return projects.stream().map(project -> ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .members(projectIdToUsersMap.getOrDefault(project.getId(), new ArrayList<>()))
                .endDate(project.getEndDate())
                .startDate(project.getStartDate())
                .description(project.getDescription())
                .status(project.getStatus())
                .projectTasks(project.getProjectTasks())
                .build()
        ).collect(Collectors.toList());
    }
}

