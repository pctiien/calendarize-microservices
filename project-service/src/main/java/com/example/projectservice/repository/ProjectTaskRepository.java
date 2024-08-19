package com.example.projectservice.repository;

import com.example.projectservice.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask,Long> {
    Optional<List<ProjectTask>> findAllByProjectId(Long projectId);
}
