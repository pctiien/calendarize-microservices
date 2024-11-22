package com.example.projectservice.service.project;

import com.example.projectservice.client.AuthServiceClient;
import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.dto.ProjectResponseDto;
import com.example.projectservice.dto.TeamMember;
import com.example.projectservice.dto.UserDto;
import com.example.projectservice.entity.*;
import com.example.projectservice.exception.ProjectNotFoundException;
import com.example.projectservice.exception.ProjectRoleNotFoundException;
import com.example.projectservice.exception.ProjectUserNotFoundException;
import com.example.projectservice.exception.UserNotFoundException;
import com.example.projectservice.mapper.ProjectMapper;
import com.example.projectservice.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final AuthServiceClient authServiceClient;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRoleRepository projectRoleRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(ProjectDto dto) {
        Project project = ProjectMapper.mapToProject(dto);
        ProjectUser projectOwner = new ProjectUser(dto.getHostId(),project);
        projectOwner.addProjectRole(projectRoleRepository.findByName(ProjectRoleName.PROJECT_OWNER));
        projectOwner.addProjectRole(projectRoleRepository.findByName(ProjectRoleName.PROJECT_CONTRIBUTOR));
        projectOwner.addProjectRole(projectRoleRepository.findByName(ProjectRoleName.PROJECT_VIEWER));
        projectOwner.addProjectRole(projectRoleRepository.findByName(ProjectRoleName.PROJECT_MEMBER));

        project.addMemberToProject(projectOwner);
        projectRepository.save(project);
        return project;
    }

    @Override
    public Project getProjectByProjectId(Long projectId) {

        return projectRepository.findById(projectId).orElseThrow(()->new ProjectNotFoundException("id",projectId.toString()));
    }

    @Override
    public ProjectResponseDto getProjectByIdBetween(Long projectId, LocalDate from, LocalDate to) {
        LocalDateTime startDate = from.atStartOfDay();
        LocalDateTime endDate = to.atTime(LocalTime.MAX);

        Project project = projectRepository.findById(projectId).orElseThrow(()->new ProjectNotFoundException("id",projectId.toString()));
        List<UserDto> members = getProjectMembersByProjectId(projectId);

        return ProjectResponseDto.builder()
                .id(project.getId())
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
                                .projectTasks(projectTaskRepository.findAllByUserIdBetween(m.getId(),startDate,endDate).stream().sorted(Comparator.comparing(ProjectTask::getStartDate)).toList())
                                .build()
                ).toList())
                .build() ;
    }

    @Override
    public List<ProjectDto> getProjectsByUserId(Long userId) {
        List<Project> projects = projectRepository.findAllByUserId(userId).orElse(new ArrayList<>());
        return projects.stream().map(p -> ProjectDto.builder()
                .id(p.getId())
                .name(p.getName())
                .status(p.getStatus())
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .build()
        ).collect(Collectors.toList()) ;
    }

    @Transactional
    @Override
    public void addMemberToProject(Long projectId, String userEmail) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new ProjectNotFoundException("id", projectId.toString()));
        UserDto user = authServiceClient.getUserByEmail(userEmail).getBody();
        if(user == null){
            throw new UserNotFoundException("email",userEmail);
        }
        Optional<ProjectUser> projectUser = projectUserRepository.findByProjectAndUserId(project,user.getId());
        if(projectUser.isEmpty())
        {
            ProjectUser _projectUser = new ProjectUser(user.getId(),project);
            ProjectRole memberRole = projectRoleRepository.findByName(ProjectRoleName.PROJECT_MEMBER);
            _projectUser.addProjectRole(memberRole);
            projectUserRepository.save(_projectUser);
        }

    }
    @Transactional
    @Override
    public Project assignRole(Long projectId, Long userId, Long projectRoleId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new ProjectNotFoundException("id",projectId.toString()));
        ProjectUser projectUser = projectUserRepository.findByProjectAndUserId(project,userId).orElseThrow(()->new ProjectUserNotFoundException("id",userId.toString()));
        ProjectRole projectRole = projectRoleRepository.findById(projectRoleId).orElseThrow(()->new ProjectRoleNotFoundException("id",projectRoleId.toString()));
        boolean roleAssigned = projectUser.getProjectRoles().stream()
                .anyMatch(role -> role.getId().equals(projectRoleId));

        if (!roleAssigned) {
            projectUser.addProjectRole(projectRole);
            projectUserRepository.save(projectUser);
        }

        return project;
    }

    private List<UserDto> getProjectMembersByProjectId(Long projectId)
    {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new ProjectNotFoundException("id",projectId.toString()));

        List<ProjectUser> projectMembers = projectUserRepository.findAllByProject(project).orElse(new ArrayList<>());


        List<Long> teamMemberIds = projectMembers.stream()
                .map(ProjectUser::getUserId)
                .distinct()
                .toList();
        return teamMemberIds.stream().map(mem->authServiceClient.getUserById(mem).getBody()).toList();
    }
}

