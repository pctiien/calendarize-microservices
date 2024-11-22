package com.example.projectservice.repository;

import com.example.projectservice.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask,Long> {
    Optional<List<ProjectTask>> findAllByProjectId(Long projectId);

    @Query("Select pt from ProjectTask pt join pt.projectUsers pu where pu.userId=:userId")
    List<ProjectTask> findAllByUserId(Long userId);

    @Query("Select pt from ProjectTask pt join ProjectUser pu where pu.userId=:userId and pt.startDate>=:from and pt.startDate <= :to " )
    List<ProjectTask> findAllByUserIdBetween(Long userId, LocalDateTime from, LocalDateTime to);
}
