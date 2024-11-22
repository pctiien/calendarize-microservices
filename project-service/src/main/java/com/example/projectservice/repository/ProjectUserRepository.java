package com.example.projectservice.repository;

import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser,Long> {
    Optional<List<ProjectUser>> findAllByProject(Project project);
    Optional<ProjectUser> findByProjectAndUserId(Project project, Long userId);
    List<ProjectUser> findByUserId(Long userId);
}
