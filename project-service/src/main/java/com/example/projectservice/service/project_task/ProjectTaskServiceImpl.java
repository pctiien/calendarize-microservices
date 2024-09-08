package com.example.projectservice.service.project_task;

import com.example.projectservice.client.AuthServiceClient;
import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.dto.UserDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectTask;
import com.example.projectservice.entity.Status;
import com.example.projectservice.entity.TaskMember;
import com.example.projectservice.exception.ProjectNotFoundException;
import com.example.projectservice.exception.TaskNotFoundException;
import com.example.projectservice.exception.UserNotFoundException;
import com.example.projectservice.mapper.ProjectTaskMapper;
import com.example.projectservice.repository.ProjectRepository;
import com.example.projectservice.repository.ProjectTaskRepository;
import com.example.projectservice.repository.TaskMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImpl implements IProjectTaskService{
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMemberRepository taskMemberRepository;
    private final AuthServiceClient authServiceClient;

    @Override
    public List<ProjectTask> getAllProjectTasks() {
        return projectTaskRepository.findAll();
    }

    @Override
    public List<ProjectTask> getProjectTasksByProjectId(Long projectId) {
        return projectTaskRepository.findAllByProjectId(projectId).orElse(new ArrayList<>());
    }

    @Transactional
    @Override
    public ProjectTask createProjectTask(ProjectTaskDto dto) {
        Project project =  projectRepository.findById(dto.getProjectId()).orElseThrow(()-> new ProjectNotFoundException("id",dto.getProjectId().toString()));
        ProjectTask projectTask = ProjectTaskMapper.mapToProjectTask(dto);
        projectTask.setProject(project);
        projectTask = projectTaskRepository.save(projectTask);
        return projectTask;
    }

    @Transactional
    @Override
    public void doneProjectTask(Long projectTaskId) {
        ProjectTask projectTask = projectTaskRepository.findById(projectTaskId).orElseThrow(()-> new ProjectNotFoundException("id",projectTaskId.toString()));
        projectTask.setStatus(Status.COMPLETED);
        projectTaskRepository.save(projectTask);
    }

    @Transactional
    @Override
    public void assignTo(Long projectTaskId, Long userId) {
        taskMemberRepository.save(new TaskMember(projectTaskId,userId));
    }

    @Override
    public void assignTo(Long projectTaskId, String email) {
        UserDto userDto = authServiceClient.getUserByEmail(email).getBody();
        if(userDto!=null)
        {
            taskMemberRepository.save(new TaskMember(projectTaskId,userDto.getId()));
        }else{
            throw new UserNotFoundException("email",email);
        }
    }

    @Transactional
    @Override
    public void deleteTask(Long projectTaskId) {
        projectTaskRepository.deleteById(projectTaskId);
        taskMemberRepository.deleteAllByTaskId(projectTaskId);
    }

    @Override
    public ProjectTask editProjectTask(ProjectTaskDto dto) {
        ProjectTask projectTask = projectTaskRepository.findById(dto.getId()).orElseThrow(()->new TaskNotFoundException("id",dto.getId().toString()));
        ProjectTask newProjectTask = ProjectTaskMapper.mapToProjectTask(dto);
        newProjectTask.setId(projectTask.getId());
        newProjectTask.setProject(projectTask.getProject());
        newProjectTask = projectTaskRepository.save(newProjectTask);
        return newProjectTask;
    }
}




















