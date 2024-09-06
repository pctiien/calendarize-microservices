package com.example.projectservice.repository;


import com.example.projectservice.entity.TaskMember;
import com.example.projectservice.entity.TaskMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskMemberRepository extends JpaRepository<TaskMember, TaskMemberId> {
    Optional<List<TaskMember>> findAllByUserId(Long userId);
    Optional<List<TaskMember>> findAllByTaskId(Long taskId);

    @Query("Select tm from TaskMember tm join ProjectTask pt on pt.id = tm.taskId where pt.project.id = :projectId")
    Optional<List<TaskMember>> findAllByProjectId(Long projectId);

    void deleteAllByTaskId(Long taskId);
}
