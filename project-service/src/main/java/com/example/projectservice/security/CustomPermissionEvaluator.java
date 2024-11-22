package com.example.projectservice.security;

import com.example.projectservice.entity.ProjectTask;
import com.example.projectservice.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

@Service
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || targetId == null || targetType == null || permission == null) {
            return false;
        }
        if(targetType.equals("TASK"))
        {
            return hasPermissionForTask(authentication,targetId,permission);
        }
        if(targetType.equals("PROJECT"))
        {
            return hasPermissionForProject(authentication,targetId,permission);
        }
        return false;
    }
    private boolean hasPermissionForProject(Authentication authentication, Serializable projectId, Object permission) {

        String requiredAuthority = String.format("%s_%s_%s","PROJECT",projectId.toString(),permission.toString());
        return authentication.getAuthorities().stream().anyMatch(authority-> {
            return authority.getAuthority().equals(requiredAuthority);
        });
    }
    private boolean hasPermissionForTask(Authentication authentication, Serializable projectTaskId, Object permission) {

        Optional<ProjectTask> projectTask = projectTaskRepository.findById(Long.valueOf(projectTaskId.toString()));
        if(projectTask.isEmpty()) return false;
        if(projectTask.get().getProject() == null) return false;

        String requiredAuthority = String.format("%s_%s_%s","PROJECT",projectTask.get().getProject().getId().toString(),permission.toString());
        return authentication.getAuthorities().stream().anyMatch(authority-> {
            return authority.getAuthority().equals(requiredAuthority);
        });
    }
}
